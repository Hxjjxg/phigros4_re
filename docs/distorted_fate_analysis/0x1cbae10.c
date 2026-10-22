void GameInformation__CheckSongUnlock(
        GameInformation_o *this,
        System_String_o *songsId,
        int32_t *unlockType,
        System_Boolean_array **chartUnlocked,
        bool *appeared,
        System_String_o **unlockInfo,
        const MethodInfo *method)
{
  __int64 v12; // x1
  __int64 v13; // x1
  __int64 v14; // x1
  __int64 v15; // x1
  __int64 v16; // x1
  __int64 v17; // x1
  __int64 v18; // x1
  __int64 v19; // x1
  __int64 v20; // x1
  __int64 v21; // x1
  __int64 v22; // x1
  __int64 v23; // x1
  __int64 v24; // x1
  __int64 v25; // x1
  __int64 v26; // x1
  __int64 v27; // x1
  __int64 v28; // x1
  __int64 v29; // x1
  __int64 v30; // x1
  __int64 v31; // x1
  __int64 v32; // x1
  __int64 v33; // x1
  __int64 v34; // x1
  __int64 v35; // x1
  __int64 v36; // x1
  __int64 v37; // x1
  __int64 v38; // x1
  __int64 v39; // x1
  __int64 v40; // x1
  SongsItem_o *SongInformation; // x25
  const MethodInfo *method_1; // x2
  __int64 SongAllRecord; // x0
  __int64 index_2; // x1
  const MethodInfo *method_2; // x2
  struct ChartUnlock_array *unlockInfo_1; // x8
  float v47; // s0
  float v48; // s1
  float v49; // s2
  struct Chapter_array *chapters; // x8
  il2cpp_array_size_t max_length; // x10
  unsigned int max_length_1; // w9
  Chapter_o *v53; // x29
  int32_t index_1; // w27
  int32_t index; // w19
  __int64 max_length_2; // x20
  Chapter_o *v57; // x9
  struct ChapterSongInfo_o *songInfo; // x9
  struct ChapterSelectorUnlockInfo_o *unlockInfo_2; // x8
  int v60; // w8
  System_Collections_Generic_List_object__o *SongAllRecord_2; // x19
  bool v62; // w20
  int32_t i; // w22
  int32_t size; // w8
  struct ChapterSelectorUnlockInfo_o *unlockInfo_4; // x8
  float v66; // s0
  float v67; // s1
  float v68; // s2
  System_String_o **unlockInfo_5; // x19
  System_String_o *newValue; // x20
  __int64 *p_StringLiteral_6267; // x8
  float v72; // s0
  float v73; // s1
  float v74; // s2
  struct ChapterSelectorUnlockInfo_o *unlockInfo_6; // x8
  System_String_o *unlockFlag; // x2
  __int64 *p_StringLiteral_10564; // x8
  System_Collections_Generic_List_LevelRecord__o *source; // x19
  float v79; // s0
  float v80; // s1
  float v81; // s2
  GameInformation___c_c *GameInformation.__c_TypeInfo; // x8
  System_Func_object__bool__o *_9__62_0; // x22
  Il2CppObject *_; // x20
  struct ChapterSelectorUnlockInfo_o *unlockInfo_7; // x8
  float v86; // s0
  float v87; // s1
  float v88; // s2
  float v89; // s0
  float v90; // s1
  float v91; // s2
  struct ChapterSelectorUnlockInfo_o *unlockInfo_8; // x8
  struct ChapterSelectorUnlockInfo_o *unlockInfo_9; // x8
  float v94; // s0
  float v95; // s1
  float v96; // s2
  System_String_o *unlockFlag_1; // x20
  struct ChapterSongInfo_o *songInfo_1; // x8
  struct ChapterSongInfo_o *songInfo_2; // x8
  struct ChartUnlock_array *unlockInfo_10; // x8
  __int64 n2; // x9
  unsigned __int64 max_length_low_1; // x26
  char v103; // w19
  __int64 v104; // x28
  unsigned __int64 max_length_low; // x9
  struct System_Single_array *difficulty; // x10
  ChartUnlock_o *v107; // x10
  System_Boolean_array *v108; // x8
  unsigned __int64 max_length_low_2; // x9
  System_Boolean_array *v110; // x10
  unsigned __int64 v111; // x9
  unsigned __int64 v112; // x8
  bool *m_Items; // x10
  System_Boolean_array *v114; // x10
  int32_t index_3; // w22
  ChartUnlock_o *v116; // x8
  Il2CppObject *Item; // x20
  float v118; // s0
  float v119; // s1
  float v120; // s2
  System_Boolean_array *v121; // x10
  int32_t index_4; // w22
  ChartUnlock_o *v123; // x8
  Il2CppObject *Item_1; // x20
  float v125; // s0
  float v126; // s1
  float v127; // s2
  System_Boolean_array *v128; // x8
  System_Boolean_array *v129; // x10
  unsigned __int64 v130; // x9
  unsigned __int64 v131; // x8
  bool *m_Items_1; // x10
  const MethodInfo *method_3; // x3
  float v134; // s0
  float v135; // s1
  float v136; // s2
  bool *appeared_2; // x22
  int32_t *unlockType_2; // x26
  __int64 SongAllRecord_7; // x27
  System_Boolean_array *v140; // x8
  struct System_Single_array *difficulty_1; // x9
  float v142; // s0
  float v143; // s1
  float v144; // s2
  System_String_o *Item_2; // x0
  System_Collections_Generic_List_object__o *v146; // x0
  int32_t index_5; // w19
  Il2CppObject *Item_3; // x20
  float v149; // s0
  float v150; // s1
  float v151; // s2
  int32_t index_6; // w19
  Il2CppObject *Item_7; // x20
  float v154; // s0
  float v155; // s1
  float v156; // s2
  Il2CppObject *Item_4; // x0
  System_String_o *keyName; // x19
  float v159; // s0
  float v160; // s1
  float v161; // s2
  Il2CppObject *Item_5; // x0
  System_String_o *keyName_1; // x19
  float v164; // s0
  float v165; // s1
  float v166; // s2
  Il2CppObject *Item_6; // x0
  System_String_o *keyName_2; // x19
  float v169; // s0
  float v170; // s1
  float v171; // s2
  int n4; // w8
  bool v173; // w8
  int32_t index_7; // w19
  Il2CppObject *Item_8; // x20
  float v176; // s0
  float v177; // s1
  float v178; // s2
  int32_t index_8; // w19
  Il2CppObject *Item_9; // x20
  float v181; // s0
  float v182; // s1
  float v183; // s2
  __int64 SongAllRecord_5; // [xsp+8h] [xbp-B8h]
  System_Collections_Generic_List_object__o *SongAllRecord_6; // [xsp+10h] [xbp-B0h]
  System_String_o **unlockInfo_3; // [xsp+18h] [xbp-A8h]
  int32_t *unlockType_1; // [xsp+20h] [xbp-A0h]
  bool *appeared_1; // [xsp+28h] [xbp-98h]
  unsigned __int64 n2_1; // [xsp+30h] [xbp-90h]
  System_Collections_Generic_List_object__o *SongAllRecord_1; // [xsp+38h] [xbp-88h]
  __int64 SongAllRecord_4; // [xsp+40h] [xbp-80h]
  char SongAllRecord_3; // [xsp+4Ch] [xbp-74h]

  if ( (byte_446E353 & 1) == 0 )
  {
    sub_1B622A0(&bool___TypeInfo, songsId);
    sub_1B622A0(&Method_System_Linq_Enumerable_All_LevelRecord___, v12);
    sub_1B622A0(&System_Func_LevelRecord__bool__TypeInfo, v13);
    sub_1B622A0(&GameInformation_TypeInfo, v14);
    sub_1B622A0(&Method_System_Collections_Generic_List_ChapterSongItem__get_Count__, v15);
    sub_1B622A0(&Method_System_Collections_Generic_List_LevelRecord__get_Count__, v16);
    sub_1B622A0(&Method_System_Collections_Generic_List_string__get_Count__, v17);
    sub_1B622A0(&Method_System_Collections_Generic_List_string__get_Item__, v18);
    sub_1B622A0(&Method_System_Collections_Generic_List_LevelRecord__get_Item__, v19);
    sub_1B622A0(&Method_System_Collections_Generic_List_ChapterSongItem__get_Item__, v20);
    sub_1B622A0(&MultiLanguageTextBase_TypeInfo, v21);
    sub_1B622A0(&SaveManagement_TypeInfo, v22);
    sub_1B622A0(&Method_GameInformation___c__CheckSongUnlock_b__62_0__, v23);
    sub_1B622A0(&GameInformation___c_TypeInfo, v24);
    sub_1B622A0(&"SideStory4UnlockTip", v25);
    sub_1B622A0(&"HD", v26);
    sub_1B622A0(&"finishLegacyChapter", v27);
    sub_1B622A0(&"ChapterUnlockTip92w", v28);
    sub_1B622A0(&"TrueHomeTrueWorldRework.816ThreeNumbers.0", v29);
    sub_1B622A0(&"ChallengeMode", v30);
    sub_1B622A0(&"IN", v31);
    sub_1B622A0(&"MainStory9", v32);
    sub_1B622A0(&"[NumOfSongToUnlock]", v33);
    sub_1B622A0(&"[SongName]", v34);
    sub_1B622A0(&"EZ", v35);
    sub_1B622A0(&"ReadSideStory4Begin", v36);
    sub_1B622A0(&"LegacyUnlockTip", v37);
    sub_1B622A0(&"Chapter9Phase2Passed", v38);
    sub_1B622A0(&"", v39);
    sub_1B622A0(&"MainStoryChapterUnlockTip", v40);
    byte_446E353 = 1;
  }
  *unlockType = 0;
  *appeared = 0;
  *unlockInfo = (System_String_o *)"";
  SongInformation = GameInformation__GetSongInformation(this, songsId, (const MethodInfo *)unlockType);
  SongAllRecord = (__int64)GameInformation__GetSongAllRecord(this, songsId, method_1);
  SongAllRecord_1 = (System_Collections_Generic_List_object__o *)SongAllRecord;
  if ( !SongInformation )
    goto LABEL_260;
  unlockInfo_1 = SongInformation->fields.unlockInfo;
  if ( !unlockInfo_1 )
    goto LABEL_260;
  SongAllRecord = sub_1B62344(bool___TypeInfo, LODWORD(unlockInfo_1->max_length));
  *chartUnlocked = (System_Boolean_array *)SongAllRecord;
  chapters = this->fields.chapters;
  if ( !chapters )
    goto LABEL_260;
  max_length = chapters->max_length;
  if ( (int)max_length < 1 )
  {
    v53 = 0;
    index_1 = -1;
  }
  else
  {
    max_length_1 = 0;
    v53 = 0;
    index_1 = -1;
    do
    {
      if ( max_length_1 >= (unsigned int)max_length )
        goto LABEL_265;
      index = 0;
      max_length_2 = (int)max_length_1;
      while ( 1 )
      {
        v57 = chapters->m_Items[max_length_2];
        if ( !v57 )
          goto LABEL_260;
        songInfo = v57->fields.songInfo;
        if ( !songInfo )
          goto LABEL_260;
        SongAllRecord = (__int64)songInfo->fields._songsBypassLimited_k__BackingField;
        if ( !SongAllRecord )
          goto LABEL_260;
        if ( index >= *(_DWORD *)(SongAllRecord + 24) )
          break;
        SongAllRecord = (__int64)System_Collections_Generic_List_object___get_Item(
                                   (System_Collections_Generic_List_object__o *)SongAllRecord,
                                   index,
                                   (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_ChapterSongItem__get_Item__);
        if ( !SongAllRecord )
          goto LABEL_260;
        SongAllRecord = System_String__op_Inequality(*(System_String_o **)(SongAllRecord + 16), songsId, 0);
        chapters = this->fields.chapters;
        if ( (SongAllRecord & 1) != 0 )
        {
          if ( !chapters )
            goto LABEL_260;
        }
        else
        {
          if ( !chapters )
            goto LABEL_260;
          if ( (unsigned int)max_length_2 >= LODWORD(chapters->max_length) )
            goto LABEL_265;
          v53 = chapters->m_Items[max_length_2];
          index_1 = index;
        }
        ++index;
        if ( (unsigned int)max_length_2 >= LODWORD(chapters->max_length) )
          goto LABEL_265;
      }
      max_length = chapters->max_length;
      max_length_1 = max_length_2 + 1;
    }
    while ( (int)max_length_2 + 1 < (int)max_length );
  }
  if ( index_1 != -1 && v53 )
  {
    unlockInfo_2 = v53->fields.unlockInfo;
    if ( !unlockInfo_2 )
      goto LABEL_260;
    unlockInfo_3 = unlockInfo;
    unlockType_1 = unlockType;
    appeared_1 = appeared;
    switch ( unlockInfo_2->fields.unlockMode )
    {
      case 1:
        SongAllRecord = (__int64)GameInformation__GetSongAllRecord(this, unlockInfo_2->fields.unlockFlag, method_2);
        if ( !SongAllRecord )
          goto LABEL_260;
        v60 = *(_DWORD *)(SongAllRecord + 24);
        SongAllRecord_2 = (System_Collections_Generic_List_object__o *)SongAllRecord;
        v62 = v60 > 0;
        if ( v60 < 1 )
          goto LABEL_36;
        for ( i = 0; i < size; v62 = i < size )
        {
          SongAllRecord = (__int64)System_Collections_Generic_List_object___get_Item(
                                     SongAllRecord_2,
                                     i,
                                     (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_LevelRecord__get_Item__);
          if ( !SongAllRecord )
            goto LABEL_260;
          if ( *(int *)(SongAllRecord + 16) >= 880000 )
            break;
          size = SongAllRecord_2->fields._size;
          ++i;
        }
LABEL_36:
        unlockInfo_4 = v53->fields.unlockInfo;
        if ( !unlockInfo_4 )
          goto LABEL_260;
        SongAllRecord = (__int64)GameInformation__GetSongInformation(this, unlockInfo_4->fields.unlockFlag, method_2);
        unlockInfo_5 = unlockInfo;
        if ( !SongAllRecord )
          goto LABEL_260;
        SongAllRecord_3 = !v62;
        if ( index_1 )
          goto LABEL_82;
        newValue = *(System_String_o **)(SongAllRecord + 32);
        if ( !MultiLanguageTextBase_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v66, v67, v68);
        p_StringLiteral_6267 = &"MainStoryChapterUnlockTip";
LABEL_63:
        SongAllRecord = (__int64)MultiLanguageTextBase__GetNowLanguageText((System_String_o *)*p_StringLiteral_6267, 0);
        if ( !SongAllRecord )
          goto LABEL_260;
        SongAllRecord = (__int64)System_String__Replace_53489992(
                                   (System_String_o *)SongAllRecord,
                                   (System_String_o *)"[SongName]",
                                   newValue,
                                   0);
        *unlockInfo_5 = (System_String_o *)SongAllRecord;
        goto LABEL_82;
      case 2:
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v47, v48, v49);
        SongAllRecord = SaveManagement__LoadBool((System_String_o *)"finishLegacyChapter", 0, 0);
        SongAllRecord_3 = SongAllRecord ^ 1;
        if ( index_1 )
          goto LABEL_82;
        if ( !MultiLanguageTextBase_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v72, v73, v74);
        SongAllRecord = (__int64)MultiLanguageTextBase__GetNowLanguageText((System_String_o *)"LegacyUnlockTip", 0);
        unlockInfo_6 = v53->fields.unlockInfo;
        if ( !unlockInfo_6 || !SongAllRecord )
          goto LABEL_260;
        unlockFlag = unlockInfo_6->fields.unlockFlag;
        p_StringLiteral_10564 = &"[NumOfSongToUnlock]";
        goto LABEL_76;
      case 3:
        source = GameInformation__GetSongAllRecord(this, unlockInfo_2->fields.unlockFlag, method_2);
        GameInformation.__c_TypeInfo = GameInformation___c_TypeInfo;
        if ( !GameInformation___c_TypeInfo->_2.cctor_finished )
        {
          j_il2cpp_runtime_class_init_0(v79, v80, v81);
          GameInformation.__c_TypeInfo = GameInformation___c_TypeInfo;
        }
        _9__62_0 = (System_Func_object__bool__o *)GameInformation.__c_TypeInfo->static_fields->__9__62_0;
        if ( !_9__62_0 )
        {
          if ( !GameInformation.__c_TypeInfo->_2.cctor_finished )
          {
            j_il2cpp_runtime_class_init_0(v79, v80, v81);
            GameInformation.__c_TypeInfo = GameInformation___c_TypeInfo;
          }
          _ = (Il2CppObject *)GameInformation.__c_TypeInfo->static_fields->__9;
          _9__62_0 = (System_Func_object__bool__o *)sub_1B624E8(System_Func_LevelRecord__bool__TypeInfo);
          System_Func_object__bool____ctor(_9__62_0, _, Method_GameInformation___c__CheckSongUnlock_b__62_0__, 0);
          GameInformation___c_TypeInfo->static_fields->__9__62_0 = (struct System_Func_LevelRecord__bool__o *)_9__62_0;
        }
        SongAllRecord = System_Linq_Enumerable__All_object_(
                          (System_Collections_Generic_IEnumerable_TSource__o *)source,
                          (System_Func_TSource__bool__o *)_9__62_0,
                          (const MethodInfo_1F61CC4 *)Method_System_Linq_Enumerable_All_LevelRecord___);
        unlockInfo_7 = v53->fields.unlockInfo;
        SongAllRecord_3 = SongAllRecord;
        if ( !unlockInfo_7 )
          goto LABEL_260;
        SongAllRecord = (__int64)GameInformation__GetSongInformation(this, unlockInfo_7->fields.unlockFlag, method_2);
        unlockInfo_5 = unlockInfo;
        if ( !SongAllRecord )
          goto LABEL_260;
        if ( index_1 )
          goto LABEL_82;
        newValue = *(System_String_o **)(SongAllRecord + 32);
        if ( !MultiLanguageTextBase_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v86, v87, v88);
        p_StringLiteral_6267 = &"ChapterUnlockTip92w";
        goto LABEL_63;
      case 4:
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v47, v48, v49);
        SongAllRecord = SaveManagement__LoadBool((System_String_o *)"ReadSideStory4Begin", 0, 0);
        if ( (SongAllRecord & 1) != 0 )
        {
          unlockInfo_8 = v53->fields.unlockInfo;
          if ( !unlockInfo_8 )
            goto LABEL_260;
          SongAllRecord = Assets_Phigros2_Utils_Tools__IsSpecifiedSongReachScore(
                            unlockInfo_8->fields.unlockFlag,
                            880000,
                            0);
          unlockInfo_9 = v53->fields.unlockInfo;
          SongAllRecord_3 = SongAllRecord;
          if ( !unlockInfo_9 )
            goto LABEL_260;
          SongAllRecord = (__int64)GameInformation__GetSongInformation(this, unlockInfo_9->fields.unlockFlag, method_2);
          if ( !SongAllRecord )
            goto LABEL_260;
          if ( !index_1 )
          {
            unlockFlag_1 = *(System_String_o **)(SongAllRecord + 32);
            if ( !MultiLanguageTextBase_TypeInfo->_2.cctor_finished )
              j_il2cpp_runtime_class_init_0(v94, v95, v96);
            SongAllRecord = (__int64)MultiLanguageTextBase__GetNowLanguageText((System_String_o *)"MainStoryChapterUnlockTip", 0);
            if ( !SongAllRecord )
              goto LABEL_260;
            p_StringLiteral_10564 = &"[SongName]";
            unlockFlag = unlockFlag_1;
LABEL_76:
            SongAllRecord = (__int64)System_String__Replace_53489992(
                                       (System_String_o *)SongAllRecord,
                                       (System_String_o *)*p_StringLiteral_10564,
                                       unlockFlag,
                                       0);
            *unlockInfo = (System_String_o *)SongAllRecord;
          }
        }
        else
        {
          if ( !index_1 )
          {
            if ( !MultiLanguageTextBase_TypeInfo->_2.cctor_finished )
              j_il2cpp_runtime_class_init_0(v89, v90, v91);
            SongAllRecord = (__int64)MultiLanguageTextBase__GetNowLanguageText((System_String_o *)"SideStory4UnlockTip", 0);
            *unlockInfo = (System_String_o *)SongAllRecord;
          }
          SongAllRecord_3 = 1;
        }
LABEL_82:
        songInfo_1 = v53->fields.songInfo;
        if ( !songInfo_1 )
          goto LABEL_260;
        SongAllRecord = (__int64)songInfo_1->fields._songsBypassLimited_k__BackingField;
        if ( !SongAllRecord )
          goto LABEL_260;
        SongAllRecord = (__int64)System_Collections_Generic_List_object___get_Item(
                                   (System_Collections_Generic_List_object__o *)SongAllRecord,
                                   index_1,
                                   (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_ChapterSongItem__get_Item__);
        index_2 = (unsigned int)(index_1 - 1);
        SongAllRecord_4 = SongAllRecord;
        if ( index_1 < 1 )
        {
          SongAllRecord_5 = 0;
          SongAllRecord_6 = 0;
        }
        else
        {
          songInfo_2 = v53->fields.songInfo;
          if ( !songInfo_2 )
            goto LABEL_260;
          SongAllRecord = (__int64)songInfo_2->fields._songsBypassLimited_k__BackingField;
          if ( !SongAllRecord )
            goto LABEL_260;
          SongAllRecord = (__int64)System_Collections_Generic_List_object___get_Item(
                                     (System_Collections_Generic_List_object__o *)SongAllRecord,
                                     index_2,
                                     (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_ChapterSongItem__get_Item__);
          if ( !SongAllRecord )
            goto LABEL_260;
          SongAllRecord = (__int64)GameInformation__GetSongInformation(
                                     this,
                                     *(System_String_o **)(SongAllRecord + 16),
                                     method_2);
          if ( !SongAllRecord )
            goto LABEL_260;
          SongAllRecord_5 = SongAllRecord;
          SongAllRecord = (__int64)GameInformation__GetSongAllRecord(
                                     this,
                                     *(System_String_o **)(SongAllRecord + 16),
                                     method_2);
          SongAllRecord_6 = (System_Collections_Generic_List_object__o *)SongAllRecord;
        }
        unlockInfo_10 = SongInformation->fields.unlockInfo;
        if ( !unlockInfo_10 )
          goto LABEL_260;
        if ( this->fields._RankingScore_k__BackingField < 11.0 )
          n2 = 1;
        else
          n2 = 2;
        max_length_low_1 = 0;
        v103 = 0;
        n2_1 = n2;
        v104 = 1;
        break;
      default:
        SongAllRecord_3 = 0;
        goto LABEL_82;
    }
    while ( max_length_low_1 <= 3 )
    {
      max_length_low = LODWORD(unlockInfo_10->max_length);
      if ( (__int64)max_length_low_1 >= (int)max_length_low )
        break;
      difficulty = SongInformation->fields.difficulty;
      if ( !difficulty )
        goto LABEL_260;
      if ( max_length_low_1 >= LODWORD(difficulty->max_length) )
        goto LABEL_265;
      if ( fabsf(difficulty->m_Items[max_length_low_1]) >= 0.00001 )
      {
        if ( max_length_low_1 >= max_length_low )
          goto LABEL_265;
        v107 = unlockInfo_10->m_Items[max_length_low_1];
        if ( !v107 )
          goto LABEL_260;
        switch ( v107->fields.unlockType )
        {
          case 0xFFFFFFFF:
            goto LABEL_104;
          case 0:
            if ( (SongAllRecord_3 & 1) != 0 )
              break;
            if ( !SongAllRecord_4 )
              goto LABEL_260;
            if ( *(_DWORD *)(SongAllRecord_4 + 24) )
            {
              if ( max_length_low_1 <= n2_1 )
                goto LABEL_104;
              SongAllRecord = (__int64)SongAllRecord_1;
              if ( !SongAllRecord_1 )
                goto LABEL_260;
              SongAllRecord = (__int64)System_Collections_Generic_List_object___get_Item(
                                         SongAllRecord_1,
                                         (int)max_length_low_1 - 1,
                                         (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_LevelRecord__get_Item__);
              if ( !SongAllRecord )
                goto LABEL_260;
              if ( *(int *)(SongAllRecord + 16) > 919999 )
                goto LABEL_115;
              SongAllRecord = (__int64)System_Collections_Generic_List_object___get_Item(
                                         SongAllRecord_1,
                                         max_length_low_1,
                                         (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_LevelRecord__get_Item__);
              if ( !SongAllRecord )
                goto LABEL_260;
              if ( *(int *)(SongAllRecord + 16) >= 1 )
              {
LABEL_115:
                v110 = *chartUnlocked;
                if ( *chartUnlocked )
                {
                  v111 = LODWORD(v110->max_length);
                  v112 = 0;
                  m_Items = v110->m_Items;
                  while ( v112 < v111 )
                  {
                    v103 = 1;
                    m_Items[v112++] = 1;
                    if ( v104 == v112 )
                      goto LABEL_157;
                  }
                  goto LABEL_265;
                }
                goto LABEL_260;
              }
            }
            else
            {
              if ( !index_1 && max_length_low_1 <= n2_1 )
                goto LABEL_104;
              if ( !max_length_low_1 )
              {
                SongAllRecord = (__int64)SongAllRecord_6;
                if ( !SongAllRecord_6 )
                  break;
                SongAllRecord = (__int64)System_Collections_Generic_List_object___get_Item(
                                           SongAllRecord_6,
                                           0,
                                           (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_LevelRecord__get_Item__);
                if ( !SongAllRecord )
                  goto LABEL_260;
                if ( *(int *)(SongAllRecord + 16) < 880000 )
                  break;
LABEL_104:
                v108 = *chartUnlocked;
                if ( !*chartUnlocked )
                  goto LABEL_260;
                max_length_low_2 = LODWORD(v108->max_length);
LABEL_150:
                if ( max_length_low_1 >= max_length_low_2 )
                  goto LABEL_265;
                v103 = 1;
                v108->m_Items[max_length_low_1] = 1;
                break;
              }
              if ( SongAllRecord_6 && max_length_low_1 <= 2 )
              {
                SongAllRecord = (__int64)System_Collections_Generic_List_object___get_Item(
                                           SongAllRecord_6,
                                           max_length_low_1,
                                           (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_LevelRecord__get_Item__);
                if ( !SongAllRecord )
                  goto LABEL_260;
                if ( *(int *)(SongAllRecord + 16) > 879999 )
                {
LABEL_171:
                  v129 = *chartUnlocked;
                  if ( *chartUnlocked )
                  {
                    v130 = LODWORD(v129->max_length);
                    v131 = 0;
                    m_Items_1 = v129->m_Items;
                    while ( v131 < v130 )
                    {
                      v103 = 1;
                      m_Items_1[v131++] = 1;
                      if ( v104 == v131 )
                        goto LABEL_157;
                    }
LABEL_265:
                    sub_1B62500(SongAllRecord, index_2, method_2);
                  }
                  goto LABEL_260;
                }
              }
              SongAllRecord = (__int64)SongAllRecord_1;
              if ( !SongAllRecord_1 )
                goto LABEL_260;
              SongAllRecord = (__int64)System_Collections_Generic_List_object___get_Item(
                                         SongAllRecord_1,
                                         (int)max_length_low_1 - 1,
                                         (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_LevelRecord__get_Item__);
              if ( !SongAllRecord )
                goto LABEL_260;
              if ( *(int *)(SongAllRecord + 16) > 919999 )
                goto LABEL_171;
              SongAllRecord = (__int64)System_Collections_Generic_List_object___get_Item(
                                         SongAllRecord_1,
                                         max_length_low_1,
                                         (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_LevelRecord__get_Item__);
              if ( !SongAllRecord )
                goto LABEL_260;
              if ( *(int *)(SongAllRecord + 16) >= 1 )
                goto LABEL_171;
            }
            break;
          case 1:
            v114 = *chartUnlocked;
            if ( !*chartUnlocked )
              goto LABEL_260;
            if ( max_length_low_1 >= LODWORD(v114->max_length) )
              goto LABEL_265;
            v114->m_Items[max_length_low_1] = 0;
            if ( max_length_low_1 >= max_length_low )
              goto LABEL_265;
            index_3 = 0;
LABEL_124:
            v116 = unlockInfo_10->m_Items[max_length_low_1];
            if ( !v116 )
              goto LABEL_260;
            SongAllRecord = (__int64)v116->fields.unlockInfo;
            if ( !SongAllRecord )
              goto LABEL_260;
            if ( index_3 >= *(_DWORD *)(SongAllRecord + 24) )
              break;
            Item = System_Collections_Generic_List_object___get_Item(
                     (System_Collections_Generic_List_object__o *)SongAllRecord,
                     index_3,
                     (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_string__get_Item__);
            if ( !SaveManagement_TypeInfo->_2.cctor_finished )
              j_il2cpp_runtime_class_init_0(v118, v119, v120);
            SongAllRecord = SaveManagement__LoadBool((System_String_o *)Item, 0, 0);
            if ( (SongAllRecord & 1) == 0 )
            {
              unlockInfo_10 = SongInformation->fields.unlockInfo;
              if ( !unlockInfo_10 )
                goto LABEL_260;
              ++index_3;
              if ( max_length_low_1 >= LODWORD(unlockInfo_10->max_length) )
                goto LABEL_265;
              goto LABEL_124;
            }
            v108 = *chartUnlocked;
            if ( !*chartUnlocked )
              goto LABEL_260;
            max_length_low_2 = LODWORD(v108->max_length);
            goto LABEL_150;
          case 2:
            v121 = *chartUnlocked;
            if ( !*chartUnlocked )
              goto LABEL_260;
            if ( max_length_low_1 >= LODWORD(v121->max_length) )
              goto LABEL_265;
            v121->m_Items[max_length_low_1] = 1;
            if ( max_length_low_1 >= max_length_low )
              goto LABEL_265;
            index_4 = 0;
            while ( 2 )
            {
              v123 = unlockInfo_10->m_Items[max_length_low_1];
              if ( !v123 )
                goto LABEL_260;
              SongAllRecord = (__int64)v123->fields.unlockInfo;
              if ( !SongAllRecord )
                goto LABEL_260;
              if ( index_4 >= *(_DWORD *)(SongAllRecord + 24) )
              {
                v128 = *chartUnlocked;
                if ( !*chartUnlocked )
                  goto LABEL_260;
              }
              else
              {
                Item_1 = System_Collections_Generic_List_object___get_Item(
                           (System_Collections_Generic_List_object__o *)SongAllRecord,
                           index_4,
                           (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_string__get_Item__);
                if ( !SaveManagement_TypeInfo->_2.cctor_finished )
                  j_il2cpp_runtime_class_init_0(v125, v126, v127);
                SongAllRecord = SaveManagement__LoadBool((System_String_o *)Item_1, 0, 0);
                if ( (SongAllRecord & 1) != 0 )
                {
                  unlockInfo_10 = SongInformation->fields.unlockInfo;
                  if ( !unlockInfo_10 )
                    goto LABEL_260;
                  ++index_4;
                  if ( max_length_low_1 >= LODWORD(unlockInfo_10->max_length) )
                    goto LABEL_265;
                  continue;
                }
                v128 = *chartUnlocked;
                if ( !*chartUnlocked )
                  goto LABEL_260;
                if ( max_length_low_1 >= LODWORD(v128->max_length) )
                  goto LABEL_265;
                v128->m_Items[max_length_low_1] = 0;
              }
              break;
            }
            if ( max_length_low_1 >= LODWORD(v128->max_length) )
              goto LABEL_265;
            v103 |= v128->m_Items[max_length_low_1];
            break;
          default:
            break;
        }
      }
LABEL_157:
      unlockInfo_10 = SongInformation->fields.unlockInfo;
      ++max_length_low_1;
      ++v104;
      if ( !unlockInfo_10 )
        goto LABEL_260;
    }
    SongAllRecord = System_String__op_Equality(v53->fields.chapterCode, (System_String_o *)"MainStory9", 0);
    if ( (SongAllRecord & 1) != 0 )
    {
      unlockType_2 = unlockType_1;
      appeared_2 = appeared_1;
      SongAllRecord_7 = SongAllRecord_4;
      if ( !SaveManagement_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v134, v135, v136);
      SongAllRecord = SaveManagement__LoadBool((System_String_o *)"Chapter9Phase2Passed", 0, 0);
      if ( (SongAllRecord & 1) != 0 )
      {
        v140 = *chartUnlocked;
        if ( !*chartUnlocked )
          goto LABEL_260;
        if ( SLODWORD(v140->max_length) >= 4 )
        {
          difficulty_1 = SongInformation->fields.difficulty;
          if ( !difficulty_1 )
            goto LABEL_260;
          if ( SLODWORD(difficulty_1->max_length) >= 4 && difficulty_1->m_Items[3] >= 0.00001 )
          {
            v103 = 1;
            v140->m_Items[3] = 1;
          }
        }
      }
    }
    else
    {
      unlockType_2 = unlockType_1;
      appeared_2 = appeared_1;
      SongAllRecord_7 = SongAllRecord_4;
    }
    if ( !SongAllRecord_7 )
LABEL_260:
      sub_1B624F8(SongAllRecord, index_2, method_2);
    switch ( *(_DWORD *)(SongAllRecord_7 + 24) )
    {
      case 0xFFFFFFFF:
        goto LABEL_233;
      case 0:
        if ( (v103 & 1) != 0 )
          goto LABEL_233;
        *unlockType_2 = 0;
        if ( !System_String__op_Equality(*unlockInfo_3, (System_String_o *)"", 0) )
          goto LABEL_235;
        if ( !MultiLanguageTextBase_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v142, v143, v144);
        SongAllRecord = (__int64)MultiLanguageTextBase__GetNowLanguageText((System_String_o *)"MainStoryChapterUnlockTip", 0);
        if ( !SongAllRecord_5 || !SongAllRecord )
          goto LABEL_260;
        Item_2 = System_String__Replace_53489992(
                   (System_String_o *)SongAllRecord,
                   (System_String_o *)"[SongName]",
                   *(System_String_o **)(SongAllRecord_5 + 32),
                   0);
LABEL_264:
        *unlockInfo_3 = Item_2;
        goto LABEL_235;
      case 1:
        if ( GameInformation__GetRemainingUnlockTimes(this, SongInformation->fields.songsKey, 0, method_3) <= 0 )
          goto LABEL_233;
        *unlockType_2 = 1;
        v146 = *(System_Collections_Generic_List_object__o **)(SongAllRecord_7 + 32);
        if ( v146 && v146->fields._size )
          Item_2 = (System_String_o *)System_Collections_Generic_List_object___get_Item(
                                        v146,
                                        0,
                                        (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_string__get_Item__);
        else
          Item_2 = (System_String_o *)"";
        goto LABEL_264;
      case 2:
        *unlockType_2 = 2;
        SongAllRecord = *(_QWORD *)(SongAllRecord_7 + 32);
        if ( !SongAllRecord )
          goto LABEL_260;
        index_5 = 0;
        while ( 2 )
        {
          if ( index_5 >= *(_DWORD *)(SongAllRecord + 24) )
            goto LABEL_235;
          Item_3 = System_Collections_Generic_List_object___get_Item(
                     (System_Collections_Generic_List_object__o *)SongAllRecord,
                     index_5,
                     (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_string__get_Item__);
          if ( !SaveManagement_TypeInfo->_2.cctor_finished )
            j_il2cpp_runtime_class_init_0(v149, v150, v151);
          if ( !SaveManagement__LoadBool((System_String_o *)Item_3, 0, 0) )
          {
            SongAllRecord = *(_QWORD *)(SongAllRecord_7 + 32);
            ++index_5;
            if ( !SongAllRecord )
              goto LABEL_260;
            continue;
          }
          break;
        }
LABEL_233:
        n4 = -1;
        goto LABEL_234;
      case 3:
        *unlockType_2 = -1;
        SongAllRecord = *(_QWORD *)(SongAllRecord_7 + 32);
        if ( !SongAllRecord )
          goto LABEL_260;
        index_6 = 0;
        break;
      case 4:
        SongAllRecord = *(_QWORD *)(SongAllRecord_7 + 32);
        if ( !SongAllRecord )
          goto LABEL_260;
        if ( *(int *)(SongAllRecord + 24) < 1 )
          goto LABEL_262;
        Item_4 = System_Collections_Generic_List_object___get_Item(
                   (System_Collections_Generic_List_object__o *)SongAllRecord,
                   0,
                   (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_string__get_Item__);
        keyName = System_String__Concat_53436140((System_String_o *)Item_4, (System_String_o *)"EZ", 0);
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v159, v160, v161);
        if ( SaveManagement__LoadBool(keyName, 0, 0) )
          goto LABEL_233;
        SongAllRecord = *(_QWORD *)(SongAllRecord_7 + 32);
        if ( !SongAllRecord )
          goto LABEL_260;
        Item_5 = System_Collections_Generic_List_object___get_Item(
                   (System_Collections_Generic_List_object__o *)SongAllRecord,
                   0,
                   (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_string__get_Item__);
        keyName_1 = System_String__Concat_53436140((System_String_o *)Item_5, (System_String_o *)"HD", 0);
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v164, v165, v166);
        if ( SaveManagement__LoadBool(keyName_1, 0, 0) )
          goto LABEL_233;
        SongAllRecord = *(_QWORD *)(SongAllRecord_7 + 32);
        if ( !SongAllRecord )
          goto LABEL_260;
        Item_6 = System_Collections_Generic_List_object___get_Item(
                   (System_Collections_Generic_List_object__o *)SongAllRecord,
                   0,
                   (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_string__get_Item__);
        keyName_2 = System_String__Concat_53436140((System_String_o *)Item_6, (System_String_o *)"IN", 0);
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v169, v170, v171);
        if ( SaveManagement__LoadBool(keyName_2, 0, 0) )
          goto LABEL_233;
LABEL_262:
        n4 = 4;
        goto LABEL_234;
      default:
        goto LABEL_235;
    }
    while ( index_6 < *(_DWORD *)(SongAllRecord + 24) )
    {
      Item_7 = System_Collections_Generic_List_object___get_Item(
                 (System_Collections_Generic_List_object__o *)SongAllRecord,
                 index_6,
                 (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_string__get_Item__);
      if ( !SaveManagement_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v154, v155, v156);
      if ( !SaveManagement__LoadBool((System_String_o *)Item_7, 0, 0) )
      {
        n4 = 3;
LABEL_234:
        *unlockType_2 = n4;
        break;
      }
      SongAllRecord = *(_QWORD *)(SongAllRecord_7 + 32);
      ++index_6;
      if ( !SongAllRecord )
        goto LABEL_260;
    }
LABEL_235:
    if ( !System_String__op_Equality(
            GameInformation_TypeInfo->static_fields->nowChapterCode,
            (System_String_o *)"ChallengeMode",
            0)
      || *unlockType_2 == -1 )
    {
      switch ( *(_DWORD *)(SongAllRecord_7 + 40) )
      {
        case 0:
          goto LABEL_239;
        case 1:
          v173 = *unlockType_2 == -1;
          goto LABEL_241;
        case 2:
          *appeared_2 = 0;
          SongAllRecord = *(_QWORD *)(SongAllRecord_7 + 48);
          if ( !SongAllRecord )
            goto LABEL_260;
          index_7 = 0;
          while ( 2 )
          {
            if ( index_7 < *(_DWORD *)(SongAllRecord + 24) )
            {
              Item_8 = System_Collections_Generic_List_object___get_Item(
                         (System_Collections_Generic_List_object__o *)SongAllRecord,
                         index_7,
                         (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_string__get_Item__);
              if ( !SaveManagement_TypeInfo->_2.cctor_finished )
                j_il2cpp_runtime_class_init_0(v176, v177, v178);
              if ( !SaveManagement__LoadBool((System_String_o *)Item_8, 0, 0) )
              {
                SongAllRecord = *(_QWORD *)(SongAllRecord_7 + 48);
                ++index_7;
                if ( !SongAllRecord )
                  goto LABEL_260;
                continue;
              }
LABEL_239:
              v173 = 1;
LABEL_241:
              *appeared_2 = v173;
            }
            goto LABEL_242;
          }
        case 3:
          *appeared_2 = 1;
          SongAllRecord = *(_QWORD *)(SongAllRecord_7 + 48);
          if ( !SongAllRecord )
            goto LABEL_260;
          index_8 = 0;
          break;
        default:
          goto LABEL_242;
      }
      while ( index_8 < *(_DWORD *)(SongAllRecord + 24) )
      {
        Item_9 = System_Collections_Generic_List_object___get_Item(
                   (System_Collections_Generic_List_object__o *)SongAllRecord,
                   index_8,
                   (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_string__get_Item__);
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v181, v182, v183);
        if ( !SaveManagement__LoadBool((System_String_o *)Item_9, 0, 0) )
          goto LABEL_237;
        SongAllRecord = *(_QWORD *)(SongAllRecord_7 + 48);
        ++index_8;
        if ( !SongAllRecord )
          goto LABEL_260;
      }
    }
    else
    {
LABEL_237:
      *appeared_2 = 0;
    }
LABEL_242:
    if ( System_String__op_Equality(songsId, (System_String_o *)"TrueHomeTrueWorldRework.816ThreeNumbers.0", 0)
      && Phigros2_Chapter9_Phase2_Chapter9Phase2Progress__get_CurrentStep(0) <= 0 )
    {
      *unlockType_2 = 2;
      *appeared_2 = 0;
    }
  }
}
