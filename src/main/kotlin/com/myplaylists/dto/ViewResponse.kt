package com.myplaylists.dto

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.jsoup.Jsoup
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.io.IOException
import java.net.URI

class ViewResponse(
    val status: HttpStatus,
) {
    companion object {
        private val mapper = jacksonObjectMapper()
        fun ok() = ViewResponse(HttpStatus.OK)
        fun redirect() = ViewResponse(HttpStatus.MOVED_PERMANENTLY)
    }

    fun render(
        page: String,
        context: ViewContext? = null
    ): ResponseEntity<String> {
        return ResponseEntity.status(this.status)
            .contentType(MediaType.TEXT_HTML)
            .body(replaceHtml(page, context))
    }

    private fun replaceHtml(
        page: String,
        context: ViewContext?
    ): String {
        val viewFile = ClassPathResource("/next/$page")
        val doc = Jsoup.parse(viewFile.inputStream, "UTF8", "")

        val nextDataElement = doc.getElementById("__NEXT_DATA__") ?: throw IOException()
        val root = mapper.readTree(nextDataElement.html())
        val pageProps = root.at("/props/pageProps") as ObjectNode
        val pageContext = mapper.convertValue(context, JsonNode::class.java)
        pageProps.replace("pageContext", pageContext)
        nextDataElement.html(mapper.writeValueAsString(root))

        return doc.outerHtml()
    }

    fun to(url: String): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.location = URI.create(url)
        return ResponseEntity(headers, this.status)
    }
}

interface ViewContext