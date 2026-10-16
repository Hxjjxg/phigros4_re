package com.google.android.gms.games.quest;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataBufferRef;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class QuestRef extends DataBufferRef implements Quest {
    private final Game zzmy;
    private final int zzmz;

    QuestRef(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.zzmy = new GameRef(dataHolder, i);
        this.zzmz = i2;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.DataBufferRef
    public final boolean equals(Object obj) {
        return QuestEntity.zza(this, obj);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final /* synthetic */ Quest freeze() {
        return new QuestEntity(this);
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long getAcceptedTimestamp() {
        return getLong("accepted_ts");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final Uri getBannerImageUri() {
        return parseUri("quest_banner_image_uri");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String getBannerImageUrl() {
        return getString("quest_banner_image_url");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final Milestone getCurrentMilestone() {
        return zzcj().get(0);
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String getDescription() {
        return getString("quest_description");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final void getDescription(CharArrayBuffer charArrayBuffer) {
        copyToBuffer("quest_description", charArrayBuffer);
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long getEndTimestamp() {
        return getLong("quest_end_ts");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final Game getGame() {
        return this.zzmy;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final Uri getIconImageUri() {
        return parseUri("quest_icon_image_uri");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String getIconImageUrl() {
        return getString("quest_icon_image_url");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long getLastUpdatedTimestamp() {
        return getLong("quest_last_updated_ts");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String getName() {
        return getString("quest_name");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final void getName(CharArrayBuffer charArrayBuffer) {
        copyToBuffer("quest_name", charArrayBuffer);
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final String getQuestId() {
        return getString("external_quest_id");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long getStartTimestamp() {
        return getLong("quest_start_ts");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final int getState() {
        return getInteger("quest_state");
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final int getType() {
        return getInteger("quest_type");
    }

    @Override // com.google.android.gms.common.data.DataBufferRef
    public final int hashCode() {
        return QuestEntity.zza(this);
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final boolean isEndingSoon() {
        return getLong("notification_ts") <= System.currentTimeMillis() + 1800000;
    }

    public final String toString() {
        return QuestEntity.zzb(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((QuestEntity) ((Quest) freeze())).writeToParcel(parcel, i);
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final List<Milestone> zzcj() {
        ArrayList arrayList = new ArrayList(this.zzmz);
        for (int i = 0; i < this.zzmz; i++) {
            arrayList.add(new zzb(this.mDataHolder, this.mDataRow + i));
        }
        return arrayList;
    }

    @Override // com.google.android.gms.games.quest.Quest
    public final long zzck() {
        return getLong("notification_ts");
    }
}
