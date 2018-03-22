package com.tomtresansky.gradle.plugin.configurationreport.html

/**
 * This is an example of a Type-Safe Groovy-style Builder
 *
 * Builders are good for declaratively describing data in your code.
 * In this example we show how to describe an Html page in Kotlin.
 *
 * See this page for details:
 * http://kotlinlang.org/docs/reference/type-safe-builders.html
 */

interface Element {
    fun render(builder: StringBuilder, indent: String)
}

class TextElement(val text: String) : Element {
    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent$text\n")
    }
}

abstract class Tag(val name: String) : Element {
    val children = arrayListOf<Element>()
    val attributes = hashMapOf<String, String>()

    protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent<$name${renderAttributes()}>\n")
        for (c in children) {
            c.render(builder, indent + "  ")
        }
        builder.append("$indent</$name>\n")
    }

    private fun renderAttributes(): String? {
        val builder = StringBuilder()
        for (a in attributes.keys) {
            builder.append(" $a=\"${attributes[a]}\"")
        }
        return builder.toString()
    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder, "")
        return builder.toString()
    }
}

abstract class TagWithText(name: String) : Tag(name) {
    operator fun String.unaryPlus() {
        children.add(TextElement(this))
    }
}

class Html() : TagWithText("html") {
    fun head(init: Head.() -> Unit) = initTag(Head(), init)

    fun body(init: Body.() -> Unit) = initTag(Body(), init)
}

class Head() : TagWithText("head") {
    fun title(init: Title.() -> Unit) = initTag(Title(), init)
}

class Title() : TagWithText("title")

abstract class BodyTag(name: String) : TagWithText(name) {
    fun b(init: B.() -> Unit) = initTag(B(), init)
    fun p(init: P.() -> Unit) = initTag(P(), init)
    fun h1(init: H1.() -> Unit) = initTag(H1(), init)
    fun ul(init: Ul.() -> Unit) = initTag(Ul(), init)
    fun a(href: String, init: A.() -> Unit) {
        val a = initTag(A(), init)
        a.href = href
    }
    fun img(src: String, alt: String, init: Img.() -> Unit) {
        val img = initTag(Img(), init)
        img.src = src
        img.alt = alt
    }
}

class Body() : BodyTag("body")
class Ul() : BodyTag("ul") {
    fun li(init: Li.() -> Unit) = initTag(Li(), init)
}

class B() : BodyTag("b")
class Li() : BodyTag("li")
class P() : BodyTag("p")
class H1() : BodyTag("h1")

class A() : BodyTag("a") {
    public var href: String
        get() = attributes["href"]!!
        set(value) {
            attributes["href"] = value
        }
}

class Img() : BodyTag("img") {
    public var src: String
        get() = attributes["src"]!!
        set(value) {
            attributes["src"] = value
        }

    public var alt: String
        get() = attributes["alt"]!!
        set(value) {
            attributes["alt"] = value
        }
}

fun html(init: Html.() -> Unit): Html {
    val html = Html()
    html.init()
    return html
}
