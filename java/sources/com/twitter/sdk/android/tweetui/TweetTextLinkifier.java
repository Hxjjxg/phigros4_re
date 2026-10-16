package com.twitter.sdk.android.tweetui;

import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class TweetTextLinkifier {
    private static final String PHOTO_TYPE = "photo";

    private TweetTextLinkifier() {
    }

    static CharSequence linkifyUrls(FormattedTweetText formattedTweetText, LinkClickListener linkClickListener, boolean z, int i) {
        if (formattedTweetText == null) {
            return null;
        }
        if (TextUtils.isEmpty(formattedTweetText.text)) {
            return formattedTweetText.text;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(formattedTweetText.text);
        addUrlEntities(spannableStringBuilder, mergeAndSortEntities(formattedTweetText.urlEntities, formattedTweetText.mediaEntities), z ? getLastPhotoEntity(formattedTweetText) : null, linkClickListener, i);
        return spannableStringBuilder;
    }

    static List<FormattedUrlEntity> mergeAndSortEntities(List<FormattedUrlEntity> list, List<FormattedMediaEntity> list2) {
        if (list2 == null) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list);
        arrayList.addAll(list2);
        Collections.sort(arrayList, new Comparator<FormattedUrlEntity>() { // from class: com.twitter.sdk.android.tweetui.TweetTextLinkifier.1
            @Override // java.util.Comparator
            public int compare(FormattedUrlEntity formattedUrlEntity, FormattedUrlEntity formattedUrlEntity2) {
                if (formattedUrlEntity == null && formattedUrlEntity2 != null) {
                    return -1;
                }
                if (formattedUrlEntity != null && formattedUrlEntity2 == null) {
                    return 1;
                }
                if (formattedUrlEntity == null && formattedUrlEntity2 == null) {
                    return 0;
                }
                if (formattedUrlEntity.start < formattedUrlEntity2.start) {
                    return -1;
                }
                return formattedUrlEntity.start > formattedUrlEntity2.start ? 1 : 0;
            }
        });
        return arrayList;
    }

    private static void addUrlEntities(SpannableStringBuilder spannableStringBuilder, List<FormattedUrlEntity> list, FormattedMediaEntity formattedMediaEntity, final LinkClickListener linkClickListener, final int i) {
        if (list == null || list.isEmpty()) {
            return;
        }
        int i2 = 0;
        for (final FormattedUrlEntity formattedUrlEntity : list) {
            int i3 = formattedUrlEntity.start - i2;
            int i4 = formattedUrlEntity.end - i2;
            if (i3 >= 0 && i4 <= spannableStringBuilder.length()) {
                if (formattedMediaEntity != null && formattedMediaEntity.start == formattedUrlEntity.start) {
                    spannableStringBuilder.replace(i3, i4, "");
                    i2 += i4 - i3;
                } else if (!TextUtils.isEmpty(formattedUrlEntity.displayUrl)) {
                    spannableStringBuilder.replace(i3, i4, (CharSequence) formattedUrlEntity.displayUrl);
                    int length = i4 - (formattedUrlEntity.displayUrl.length() + i3);
                    i2 += length;
                    spannableStringBuilder.setSpan(new ClickableSpan() { // from class: com.twitter.sdk.android.tweetui.TweetTextLinkifier.2
                        @Override // android.text.style.ClickableSpan
                        public void onClick(View view) {
                            LinkClickListener linkClickListener2 = linkClickListener;
                            if (linkClickListener2 == null) {
                                return;
                            }
                            linkClickListener2.onUrlClicked(formattedUrlEntity.url);
                        }

                        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                        public void updateDrawState(TextPaint textPaint) {
                            textPaint.setColor(i);
                            textPaint.setUnderlineText(false);
                        }
                    }, i3, i4 - length, 33);
                }
            }
        }
    }

    private static FormattedMediaEntity getLastPhotoEntity(FormattedTweetText formattedTweetText) {
        if (formattedTweetText == null) {
            return null;
        }
        List<FormattedMediaEntity> list = formattedTweetText.mediaEntities;
        if (list.isEmpty()) {
            return null;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            FormattedMediaEntity formattedMediaEntity = list.get(size);
            if (PHOTO_TYPE.equals(formattedMediaEntity.type)) {
                return formattedMediaEntity;
            }
        }
        return null;
    }
}
