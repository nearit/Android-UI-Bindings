package com.nearit.ui_bindings_sample;

import java.util.ArrayList;
import java.util.List;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.contentplugin.model.ContentLink;
import it.near.sdk.reactions.contentplugin.model.ImageSet;
import it.near.sdk.reactions.couponplugin.model.Claim;
import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 18/09/17.
 */

class ContentFactory {

    ContentFactory() {}

    Content getCompleteContent() {
        Content content = new Content();
        content.contentString = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        content.title = "Title example";
        List<ImageSet> imageSets = new ArrayList<>();
        ImageSet image = new ImageSet();
        image.setFullSize("http://1.bp.blogspot.com/-tcw3QOw4Mj8/T1cvE9f4l9I/AAAAAAAAARQ/czokhaNJ1D4/s1600/Ziltoid.jpg");
        imageSets.add(image);
        content.setImages_links(imageSets);
        content.setCta(new ContentLink("Follow us", "https://www.facebook.com/nearitmobile/"));
        return content;
    }

    Content getNoCTAContent() {
        Content content = new Content();
        content.contentString = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        content.title = "Title example";
        List<ImageSet> imageSets = new ArrayList<>();
        ImageSet image = new ImageSet();
        image.setFullSize("http://1.bp.blogspot.com/-tcw3QOw4Mj8/T1cvE9f4l9I/AAAAAAAAARQ/czokhaNJ1D4/s1600/Ziltoid.jpg");
        imageSets.add(image);
        content.setImages_links(imageSets);
        return content;
    }

    Content getNoImageContent() {
        Content content = new Content();
        content.contentString = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
        content.title = "Title example";
        content.setCta(new ContentLink("Follow us", "https://www.facebook.com/nearitmobile/"));
        return content;
    }

}
