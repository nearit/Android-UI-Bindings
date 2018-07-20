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
        content.contentString = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali."
        content.title = "Title example"
        val image = ImageSet()
        image.fullSize = "https://www.nearit.com/wp-content/uploads/2017/06/comefunzionanearit_710x500.png"
        content.images_links = listOf(image)
        content.cta = ContentLink("Follow us", "https://www.facebook.com/nearitmobile/")
        return content
    }

    fun getNoCTAContent(): Content {
        val content = Content()
        content.contentString = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali."
        content.title = "Title example"
        val image = ImageSet()
        image.fullSize = "https://www.nearit.com/wp-content/uploads/2017/06/comefunzionanearit_710x500.png"
        content.images_links = listOf(image)
        return content
    }

    fun getNoImageContent(): Content {
        val content = Content()
        content.contentString = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali."
        content.title = "Title example"
        content.cta = ContentLink("Follow us", "https://www.facebook.com/nearitmobile/")
        return content
    }

}