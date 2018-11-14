package com.your_company.ui_bindings_sample.factories;

import java.util.ArrayList;
import java.util.List;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.contentplugin.model.ContentLink;
import it.near.sdk.reactions.contentplugin.model.ImageSet;

/**
 * @author Federico Boschini
 */

public class ContentFactory {

    public ContentFactory() {}

    public Content getCompleteContent() {
        Content content = new Content();
        content.contentString = "<a href=\"https://google.com\">LINK!</a>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        content.title = "Title example";
        List<ImageSet> imageSets = new ArrayList<>();
        ImageSet image = new ImageSet();
        image.setFullSize("https://prod-nearit-media.s3.amazonaws.com/uploads/image/image/73dd2e3c-8891-42c0-8641-ee4c53a56674/file.png");
        imageSets.add(image);
        content.setImages_links(imageSets);
        content.setCta(new ContentLink("Follow us", "https://www.facebook.com/nearitmobile/"));
        return content;
    }

    public Content getNoCTAContent() {
        Content content = new Content();
        content.contentString = "<a href=\"https://google.com\">LINK!</a>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        content.title = "Title example";
        List<ImageSet> imageSets = new ArrayList<>();
        ImageSet image = new ImageSet();
        image.setFullSize("https://prod-nearit-media.s3.amazonaws.com/uploads/image/image/73dd2e3c-8891-42c0-8641-ee4c53a56674/file.png");
        imageSets.add(image);
        content.setImages_links(imageSets);
        return content;
    }

    public Content getNoImageContent() {
        Content content = new Content();
        content.contentString = "<a href=\"https://google.com\">LINK!</a>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        content.title = "Title example";
        content.setCta(new ContentLink("Follow us", "https://www.facebook.com/nearitmobile/"));
        return content;
    }

}
