package com.your_company.ui_bindings_kotlin_sample.factories

import it.near.sdk.reactions.contentplugin.model.Content
import it.near.sdk.reactions.contentplugin.model.ContentLink
import it.near.sdk.reactions.contentplugin.model.ImageSet

/**
* @author Federico Boschini
*/

object ContentFactory {

    fun getCompleteContent(): Content {
        val content = Content()
        content.contentString = "<a href=\"https://google.com\">LINK!</a>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. https://nearit.com"
        content.title = "Title example"
        val image = ImageSet()
        image.fullSize = "https://prod-nearit-media.s3.amazonaws.com/uploads/image/image/73dd2e3c-8891-42c0-8641-ee4c53a56674/file.png"
        content.images_links = listOf(image)
        content.cta = ContentLink("Follow us", "https://www.facebook.com/nearitmobile/")
        return content
    }

    fun getNoCTAContent(): Content {
        val content = Content()
        content.contentString = "<a href=\"https://google.com\">LINK!</a>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. https://nearit.com"
        content.title = "Title example"
        val image = ImageSet()
        image.fullSize = "https://prod-nearit-media.s3.amazonaws.com/uploads/image/image/73dd2e3c-8891-42c0-8641-ee4c53a56674/file.png"
        content.images_links = listOf(image)
        return content
    }

    fun getNoImageContent(): Content {
        val content = Content()
        content.contentString = "<a href=\"https://google.com\">LINK!</a>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. https://nearit.com"
        content.title = "Title example"
        content.cta = ContentLink("Follow us", "https://www.facebook.com/nearitmobile/")
        return content
    }

}