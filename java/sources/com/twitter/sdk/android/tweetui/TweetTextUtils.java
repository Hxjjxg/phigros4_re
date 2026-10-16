package com.twitter.sdk.android.tweetui;

import android.text.TextUtils;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetEntities;
import com.twitter.sdk.android.core.models.UrlEntity;
import com.twitter.sdk.android.tweetui.internal.util.HtmlEntities;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class TweetTextUtils {
    private static final String PHOTO_TYPE = "photo";

    private TweetTextUtils() {
    }

    static FormattedTweetText formatTweetText(Tweet tweet) {
        if (tweet == null) {
            return null;
        }
        FormattedTweetText formattedTweetText = new FormattedTweetText();
        convertEntities(formattedTweetText, tweet);
        format(formattedTweetText, tweet);
        return formattedTweetText;
    }

    static void convertEntities(FormattedTweetText formattedTweetText, Tweet tweet) {
        if (tweet.entities == null) {
            return;
        }
        List<UrlEntity> list = tweet.entities.urls;
        if (list != null) {
            Iterator<UrlEntity> it = list.iterator();
            while (it.hasNext()) {
                formattedTweetText.urlEntities.add(new FormattedUrlEntity(it.next()));
            }
        }
        List<MediaEntity> list2 = tweet.entities.media;
        if (list2 != null) {
            Iterator<MediaEntity> it2 = list2.iterator();
            while (it2.hasNext()) {
                formattedTweetText.mediaEntities.add(new FormattedMediaEntity(it2.next()));
            }
        }
    }

    static void format(FormattedTweetText formattedTweetText, Tweet tweet) {
        if (TextUtils.isEmpty(tweet.text)) {
            return;
        }
        HtmlEntities.Unescaped unescapedUnescape = HtmlEntities.HTML40.unescape(tweet.text);
        StringBuilder sb = new StringBuilder(unescapedUnescape.unescaped);
        adjustIndicesForEscapedChars(formattedTweetText.urlEntities, unescapedUnescape.indices);
        adjustIndicesForEscapedChars(formattedTweetText.mediaEntities, unescapedUnescape.indices);
        adjustIndicesForSupplementaryChars(sb, formattedTweetText);
        formattedTweetText.text = sb.toString();
    }

    static void adjustIndicesForEscapedChars(List<? extends FormattedUrlEntity> list, List<int[]> list2) {
        if (list == null || list2 == null || list2.isEmpty()) {
            return;
        }
        int size = list2.size();
        int i = 0;
        int i2 = 0;
        for (FormattedUrlEntity formattedUrlEntity : list) {
            int i3 = 0;
            int i4 = i2;
            int i5 = i;
            while (i < size) {
                int[] iArr = list2.get(i);
                int i6 = iArr[0];
                int i7 = iArr[1];
                int i8 = i7 - i6;
                if (i7 < formattedUrlEntity.start) {
                    i4 += i8;
                    i5++;
                } else if (i7 < formattedUrlEntity.end) {
                    i3 += i8;
                }
                i++;
            }
            formattedUrlEntity.start -= i4;
            formattedUrlEntity.end -= i3 + i4;
            i = i5;
            i2 = i4;
        }
    }

    static void adjustIndicesForSupplementaryChars(StringBuilder sb, FormattedTweetText formattedTweetText) {
        ArrayList arrayList = new ArrayList();
        int length = sb.length() - 1;
        for (int i = 0; i < length; i++) {
            if (Character.isHighSurrogate(sb.charAt(i)) && Character.isLowSurrogate(sb.charAt(i + 1))) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        adjustEntitiesWithOffsets(formattedTweetText.urlEntities, arrayList);
        adjustEntitiesWithOffsets(formattedTweetText.mediaEntities, arrayList);
    }

    static void adjustEntitiesWithOffsets(List<? extends FormattedUrlEntity> list, List<Integer> list2) {
        if (list == null || list2 == null) {
            return;
        }
        for (FormattedUrlEntity formattedUrlEntity : list) {
            int i = formattedUrlEntity.start;
            Iterator<Integer> it = list2.iterator();
            int i2 = 0;
            while (it.hasNext() && it.next().intValue() - i2 <= i) {
                i2++;
            }
            formattedUrlEntity.start += i2;
            formattedUrlEntity.end += i2;
        }
    }

    static MediaEntity getLastPhotoEntity(TweetEntities tweetEntities) {
        List<MediaEntity> list;
        if (tweetEntities != null && (list = tweetEntities.media) != null && !list.isEmpty()) {
            for (int size = list.size() - 1; size >= 0; size--) {
                MediaEntity mediaEntity = list.get(size);
                if (mediaEntity.type != null && mediaEntity.type.equals(PHOTO_TYPE)) {
                    return mediaEntity;
                }
            }
        }
        return null;
    }

    static boolean hasPhotoUrl(TweetEntities tweetEntities) {
        return getLastPhotoEntity(tweetEntities) != null;
    }
}
