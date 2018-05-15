package com.nearit.ui_bindings.coupon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */
public class CouponUtils {

    static void sortByClaimedAtDate(List<Coupon> list) {
        Collections.sort(list, new Comparator<Coupon>() {
            @Override
            public int compare(Coupon c1, Coupon c2) {
                if(c1.getClaimedAtDate() == null || c2.getClaimedAt() == null) {
                    return 0;
                }
                Date c1Date = c1.getClaimedAtDate();
                return c2.getClaimedAtDate() != null ? c2.getClaimedAtDate().compareTo(c1Date) : 0;
            }
        });
    }

    static void excludeRedeemed(List<Coupon> couponList) {
        Iterator<Coupon> iterator = couponList.iterator();
        while (iterator.hasNext()) {
            Coupon c = iterator.next();
            if (c.getRedeemedAtDate() != null) {
                iterator.remove();
            }
        }
    }

    static List<Coupon> getValid(List<Coupon> couponList) {
        List<Coupon> list = new ArrayList<>(couponList);
        Iterator<Coupon> iterator = list.iterator();
        while (iterator.hasNext()) {
            Coupon c = iterator.next();
            if (c.getRedeemedAtDate() != null) {
                iterator.remove();
            }
            if (c.getExpiresAtDate() != null && c.getExpiresAtDate().getTime() < System.currentTimeMillis()) {
                iterator.remove();
            }
            if (c.getRedeemableFromDate() != null && c.getRedeemableFromDate().getTime() > System.currentTimeMillis()) {
                iterator.remove();
            }
        }
        return list;
    }

    static List<Coupon> getExpired(List<Coupon> couponList) {
        List<Coupon> list = new ArrayList<>(couponList);
        Iterator<Coupon> iterator = list.iterator();
        while (iterator.hasNext()) {
            Coupon c = iterator.next();
            if (c.getExpiresAtDate() == null) {
                iterator.remove();
            } else {
                if (c.getExpiresAtDate() != null && !(c.getExpiresAtDate().getTime() < System.currentTimeMillis())) {
                    iterator.remove();
                }
            }
        }
        return list;
    }

    static List<Coupon> getInactive(List<Coupon> couponList) {
        List<Coupon> list = new ArrayList<>(couponList);
        Iterator<Coupon> iterator = list.iterator();
        while (iterator.hasNext()) {
            Coupon c = iterator.next();
            if (c.getRedeemableFromDate() == null) {
                iterator.remove();
            }
            if (c.getRedeemableFromDate() != null && !(c.getRedeemableFromDate().getTime() > System.currentTimeMillis())) {
                iterator.remove();
            }
        }
        return list;
    }

    static List<Coupon> getRedeemed(List<Coupon> couponList) {
        List<Coupon> list = new ArrayList<>(couponList);
        Iterator<Coupon> iterator = list.iterator();
        while (iterator.hasNext()) {
            Coupon c = iterator.next();
            if (c.getRedeemedAtDate() == null) {
                iterator.remove();
            }
        }
        return list;
    }

}
