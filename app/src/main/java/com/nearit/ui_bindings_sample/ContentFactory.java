package com.nearit.ui_bindings_sample;

import java.util.ArrayList;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Claim;
import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 18/09/17.
 */

class ContentFactory {

    private Content content = new Content();

    ContentFactory() {
    }

    Content getCompleteContent() {
        content.contentString = "Long coupon description, Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna ali.";
//        content.title = "";
        return content;
    }

}
