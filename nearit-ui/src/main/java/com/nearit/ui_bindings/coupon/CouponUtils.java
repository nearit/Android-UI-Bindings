package com.nearit.ui_bindings.coupon;

import com.nearit.ui_bindings.utils.CollectionsUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import it.near.sdk.reactions.couponplugin.model.Coupon;

import static com.nearit.ui_bindings.utils.CollectionsUtils.filter;

/**
 * @author Federico Boschini
 */
public class CouponUtils {

    static void sortByClaimedAtDate(List<Coupon> list) {
        Collections.sort(list, new Comparator<Coupon>() {
            @Override
            public int compare(Coupon c1, Coupon c2) {
                if (c1.getClaimedAtDate() == null || c2.getClaimedAtDate() == null) {
                    return 0;
                }
                Date c1Date = c1.getClaimedAtDate();
                return c2.getClaimedAtDate() != null ? c2.getClaimedAtDate().compareTo(c1Date) : 0;
            }
        });
    }

    public static void excludeRedeemed(List<Coupon> couponList) {
        Iterator<Coupon> iterator = couponList.iterator();
        while (iterator.hasNext()) {
            Coupon c = iterator.next();
            if (c.getRedeemedAtDate() != null) {
                iterator.remove();
            }
        }
    }

    public static List<Coupon> getValid(List<Coupon> couponList) {
        List<Coupon> valid = filter(couponList, new CollectionsUtils.Predicate<Coupon>() {
            @Override
            public boolean apply(Coupon item) {
                return item.getRedeemedAtDate() == null &&
                        (item.getExpiresAtDate() == null || item.getExpiresAtDate().getTime() > System.currentTimeMillis()) &&
                        (item.getRedeemableFromDate() == null || item.getRedeemableFromDate().getTime() < System.currentTimeMillis());
            }
        });
        sortByClaimedAtDate(valid);
        return valid;
    }

    public static List<Coupon> getExpired(List<Coupon> couponList) {
        List<Coupon> expired = filter(couponList, new CollectionsUtils.Predicate<Coupon>() {
            @Override
            public boolean apply(Coupon item) {
                return item.getRedeemedAtDate() == null && item.getExpiresAtDate() != null && item.getExpiresAtDate().getTime() < System.currentTimeMillis();
            }
        });
        sortByClaimedAtDate(expired);
        return expired;
    }

    public static List<Coupon> getInactive(List<Coupon> couponList) {
        List<Coupon> inactive = filter(couponList, new CollectionsUtils.Predicate<Coupon>() {
            @Override
            public boolean apply(Coupon item) {
                return item.getRedeemableFromDate() != null && item.getRedeemableFromDate().getTime() > System.currentTimeMillis();
            }
        });
        sortByClaimedAtDate(inactive);
        return inactive;
    }

    public static List<Coupon> getRedeemed(List<Coupon> couponList) {
        List<Coupon> redeemed = filter(couponList, new CollectionsUtils.Predicate<Coupon>() {
            @Override
            public boolean apply(Coupon item) {
                return item.getRedeemedAtDate() != null;
            }
        });
        sortByClaimedAtDate(redeemed);
        return redeemed;
    }

}
