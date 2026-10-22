void ChapterSelector__Start(ChapterSelector_o *this, const MethodInfo *method)
{
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  __int64 v9; // x1
  __int64 v10; // x1
  __int64 v11; // x1
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
  __int64 v41; // x1
  __int64 v42; // x1
  __int64 v43; // x1
  __int64 v44; // x1
  __int64 v45; // x1
  __int64 v46; // x1
  __int64 v47; // x1
  __int64 v48; // x1
  __int64 v49; // x1
  __int64 v50; // x1
  __int64 v51; // x1
  __int64 v52; // x1
  __int64 v53; // x1
  __int64 v54; // x1
  __int64 v55; // x1
  __int64 chapterPanel; // x0
  int32_t childCount; // w20
  float v58; // s0
  float v59; // s1
  float v60; // s2
  System_String_o *String; // x0
  UnityEngine_RectTransform_o *chapterPanel_1; // x21
  int v63; // w20
  const MethodInfo *method_2; // x2
  float v65; // s0
  float v66; // s1
  float v67; // s2
  struct ChapterInfo_array **p_chapterInfos; // x28
  __int64 v69; // x22
  __int64 n4; // x29
  int v71; // w8
  unsigned int v72; // w27
  __int64 v73; // x8
  __int64 object; // x23
  struct ChapterInfo_array *chapterInfos_1; // x8
  __int64 v76; // x8
  struct ChapterInfo_array *chapterInfos_2; // x8
  __int64 v78; // x9
  __int64 v79; // x9
  __int64 v80; // x8
  __int64 v81; // x8
  UnityEngine_Events_UnityEvent_o *v82; // x25
  __int64 chapterPanel_2; // x24
  UnityEngine_Events_UnityAction_o *call; // x26
  struct ChapterInfo_array *chapterInfos_3; // x8
  __int64 v86; // x9
  __int64 v87; // x8
  struct ChapterInfo_array *chapterInfos_4; // x8
  __int64 v89; // x9
  __int64 v90; // x8
  struct ChapterInfo_array *chapterInfos_5; // x8
  __int64 v92; // x9
  __int64 v93; // x8
  struct ChapterInfo_array *chapterInfos_6; // x8
  __int64 v95; // x9
  __int64 v96; // x8
  __int64 v97; // x8
  struct ChapterInfo_array *chapterInfos_7; // x9
  __int64 v99; // x10
  __int64 v100; // x20
  struct ChapterInfo_array *chapterInfos_8; // x8
  __int64 v102; // x20
  __int64 v103; // x8
  System_String_o *str0; // x25
  System_String_o *keyName_2; // x26
  float v106; // s0
  float v107; // s1
  float v108; // s2
  System_String_o *keyName_1; // x26
  float v110; // s0
  float v111; // s1
  float v112; // s2
  System_String_o *keyName; // x25
  float v114; // s0
  float v115; // s1
  float v116; // s2
  struct ChapterInfo_array *chapterInfos_9; // x8
  __int64 v118; // x9
  __int64 v119; // x10
  __int64 v120; // x8
  float v121; // s0
  float v122; // s1
  float v123; // s2
  float v124; // s0
  float v125; // s1
  float v126; // s2
  struct ChapterInfo_array *chapterInfos_10; // x8
  __int64 v128; // x20
  struct ChapterInfo_array *chapterInfos_11; // x8
  __int64 v130; // x20
  float v131; // s0
  float v132; // s1
  float v133; // s2
  struct ChapterInfo_array *chapterInfos_12; // x8
  __int64 v135; // x20
  struct ChapterInfo_array *chapterInfos_13; // x8
  __int64 v137; // x20
  __int64 *p_StringLiteral_6258; // x8
  struct ChapterInfo_array *chapterInfos_14; // x8
  float v140; // s0
  float v141; // s1
  float v142; // s2
  struct Phigros2_VisionReplay_VisionReplay_StaticFields *static_fields; // x8
  Phigros2_VisionReplay_VisionReplay_Key_c *Phigros2.VisionReplay.VisionReplay.Key_TypeInfo; // x0
  System_String_o *Chapter8Passed_k__BackingField; // x24
  float v146; // s0
  float v147; // s1
  float v148; // s2
  struct ChapterInfo_array *chapterInfos_15; // x8
  __int64 v150; // x20
  struct ChapterInfo_array *chapterInfos_16; // x8
  __int64 v152; // x20
  Phigros2_VisionReplay_VisionReplay_Key_c *Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1; // x0
  System_String_o *Chapter8UnlockSecondPhase_k__BackingField; // x24
  struct ChapterInfo_array *chapterInfos_17; // x8
  __int64 v156; // x20
  struct ChapterInfo_array *chapterInfos_18; // x8
  __int64 v158; // x20
  __int64 *p_StringLiteral_6258_1; // x8
  struct ChapterInfo_array *chapterInfos_19; // x8
  float v161; // s0
  float v162; // s1
  float v163; // s2
  struct ChapterInfo_array *chapterInfos_20; // x8
  __int64 v165; // x20
  struct ChapterInfo_array *chapterInfos_21; // x8
  __int64 v167; // x20
  struct ChapterInfo_array *chapterInfos_22; // x8
  __int64 v169; // x8
  struct ChapterInfo_array *chapterInfos_23; // x8
  __int64 v171; // x8
  struct ChapterInfo_array *chapterInfos_24; // x8
  __int64 v173; // x8
  struct ChapterInfo_array *chapterInfos_25; // x8
  __int64 v175; // x8
  struct ChapterInfo_array *chapterInfos_26; // x8
  __int64 v177; // x8
  __int64 v178; // x8
  UnityEngine_Events_UnityEvent_o *v179; // x24
  UnityEngine_Events_UnityAction_o *call_1; // x25
  UnityEngine_RectTransform_o *chapterPanel_3; // x22
  struct UnityEngine_Vector2_StaticFields *static_fields_1; // x8
  float x; // s8
  float y; // s9
  float Float; // s0
  struct ChapterInfo_array *chapterInfos; // x8
  il2cpp_array_size_t max_length; // x9
  il2cpp_array_size_t max_length_1; // x9
  int32_t nowChapterIndex; // w9
  int32_t _nowChapterIndex; // w8
  __int64 _nowChapterIndex_1; // x9
  struct ChapterInfo_array *chapterInfos_27; // x8
  ChapterInfo_o *v193; // x8
  struct ChapterInfo_array *_chapterInfos; // x8
  __int64 _nowChapterIndex_2; // x9
  ChapterInfo_o *v196; // x8
  struct ChapterInfo_array *_chapterInfos_1; // x8
  __int64 _nowChapterIndex_3; // x9
  ChapterInfo_o *v199; // x8
  float v200; // s0
  float v201; // s1
  float v202; // s2
  float v203; // s0
  float v204; // s1
  float v205; // s2
  __int64 v206; // x8
  struct AddressableImageLoader_array *background; // x8
  AddressableImageLoader_o *v208; // x22
  float v209; // s0
  float v210; // s1
  float v211; // s2
  struct ChapterInfo_array *chapterInfos_28; // x8
  int max_length_3; // w26
  il2cpp_array_size_t max_length_2; // x9
  ChapterInfo_o *v215; // x8
  struct ChapterInfo_array *chapterInfos_29; // x8
  il2cpp_array_size_t max_length_4; // x9
  ChapterInfo_o *v218; // x10
  struct ChapterSongInfo_o *songInfo; // x10
  struct System_Collections_Generic_List_ChapterSongItem__o *songsBypassLimited_k__BackingField; // x10
  int32_t size; // w10
  int32_t _size; // w22
  ChapterInfo_o *v223; // x9
  struct ChapterSongInfo_o *songInfo_1; // x10
  struct System_Collections_Generic_List_ChapterSongItem__o *_songsBypassLimited_k__BackingField; // x10
  ChapterInfo_o *v226; // x8
  struct ChapterSongInfo_o *songInfo_2; // x8
  GameInformation_o *main; // x23
  int v229; // w8
  System_Collections_Generic_List_object__o *chapterPanel_4; // x23
  bool v231; // w20
  int32_t index; // w24
  int32_t _size_1; // w8
  struct ChapterInfo_array *chapterInfos_30; // x8
  ChapterInfo_o *v235; // x8
  struct ChapterSongInfo_o *songInfo_3; // x8
  struct UnityEngine_UI_Text_o *allSongNum; // x22
  struct ChapterInfo_array *chapterInfos_31; // x8
  ChapterInfo_o *v239; // x8
  struct UnityEngine_UI_Text_o *clearSongNum; // x22
  struct ChapterInfo_array *chapterInfos_32; // x8
  ChapterInfo_o *v242; // x8
  struct UnityEngine_UI_Text_o *fullComboSongNum; // x22
  struct ChapterInfo_array *chapterInfos_33; // x8
  ChapterInfo_o *v245; // x8
  struct UnityEngine_UI_Text_o *phiSongNum; // x22
  struct ChapterInfo_array *chapterInfos_34; // x8
  ChapterInfo_o *v248; // x8
  struct GameInformation_o *_main; // x8
  ChapterSelector___c_c *ChapterSelector.__c_TypeInfo; // x0
  System_Collections_Generic_IEnumerable_TSource__o *chapters; // x22
  System_Func_object__bool__o *_9__18_3; // x23
  Il2CppObject *_; // x24
  float v254; // s0
  float v255; // s1
  float v256; // s2
  __int64 v257; // x8
  ChapterSelector___c_c *ChapterSelector.__c_TypeInfo_1; // x0
  System_Collections_Generic_IEnumerable_TSource__o *source; // x22
  System_Func_object__object__o *_9__18_4; // x23
  Il2CppObject *__1; // x24
  System_Collections_Generic_IEnumerable_TSource__o *source_1; // x0
  float v263; // s0
  float v264; // s1
  float v265; // s2
  __int64 chapterPanel_5; // x22
  struct Phigros2_VisionReplay_VisionReplay_StaticFields *static_fields_2; // x8
  struct GameInformation_o *_main_1; // x8
  ChapterSelector___c_c *ChapterSelector.__c_TypeInfo_2; // x0
  System_Collections_Generic_IEnumerable_TSource__o *chapters_1; // x23
  System_Func_object__bool__o *_9__18_5; // x24
  Il2CppObject *__2; // x20
  float v273; // s0
  float v274; // s1
  float v275; // s2
  __int64 v276; // x8
  ChapterSelector___c_c *ChapterSelector.__c_TypeInfo_3; // x0
  System_Collections_Generic_IEnumerable_TSource__o *source_2; // x23
  System_Func_object__object__o *_9__18_6; // x24
  Il2CppObject *__3; // x20
  System_Collections_Generic_IEnumerable_TSource__o *source_3; // x0
  __int64 v282; // x8
  __int64 chapterPanel_6; // x23
  unsigned __int64 v284; // x21
  System_String_o *key; // x24
  float v286; // s0
  float v287; // s1
  float v288; // s2
  System_Collections_Generic_List_object__o *chapterPanel_7; // x24
  struct ChapterInfo_array *chapterInfos_35; // x8
  ChapterInfo_o *v291; // x8
  struct UnityEngine_UI_Text_o *clearSongNum_1; // x22
  struct ChapterInfo_array *chapterInfos_36; // x8
  ChapterInfo_o *v294; // x8
  struct UnityEngine_UI_Text_o *fullComboSongNum_1; // x22
  struct ChapterInfo_array *chapterInfos_37; // x8
  ChapterInfo_o *v297; // x8
  struct UnityEngine_UI_Text_o *phiSongNum_1; // x22
  __int64 v299; // x8
  unsigned __int64 v300; // x20
  System_String_o *keyName_3; // x23
  float v302; // s0
  float v303; // s1
  float v304; // s2
  struct ChapterInfo_array *chapterInfos_38; // x8
  ChapterInfo_o *v306; // x8
  struct UnityEngine_UI_Text_o *allSongNum_1; // x22
  __int64 n4_1; // x22
  unsigned int v309; // w29
  __int64 v310; // x8
  __int64 v311; // x8
  struct System_String_o *unlockFlag; // x20
  int32_t LegacyChapterSongFinishNum_18_10; // w21
  int32_t LegacyChapterSongFinishNum_18; // w20
  float v315; // s0
  float v316; // s1
  float v317; // s2
  const MethodInfo *method_1; // x4
  bool IsSpecifiedSongReachScore_18_7; // w0
  const MethodInfo *method_3; // x4
  System_String_o *"ChapterUnlockTip92w"; // x1
  float v322; // s0
  float v323; // s1
  float v324; // s2
  bool IsSpecifiedSongReachScore_18; // w20
  struct ChapterInfo_array *chapterInfos_39; // x8
  __int64 v327; // x8
  __int64 v328; // x20
  struct ChapterInfo_array *chapterInfos_40; // x8
  __int64 v330; // x8
  struct ChapterInfo_array *chapterInfos_41; // x8
  __int64 v332; // x8
  struct ChapterInfo_array *chapterInfos_42; // x8
  __int64 v334; // x8
  __int64 v335; // [xsp+8h] [xbp-98h]
  ChapterSelector___c__DisplayClass18_2_o v336; // [xsp+10h] [xbp-90h] BYREF
  ChapterSelector___c__DisplayClass18_1_o v337; // [xsp+18h] [xbp-88h] BYREF
  __int64 v338; // [xsp+20h] [xbp-80h] BYREF
  __int64 v339; // [xsp+28h] [xbp-78h] BYREF
  UnityEngine_Vector2_o value; // 0:s0.4,4:s1.4
  UnityEngine_Vector2_o value_1; // 0:s0.4,4:s1.4

  if ( (byte_446E945 & 1) == 0 ) /*0x1d5361c*/
  {
    sub_1B622A0(&Method_UnityEngine_Component_GetComponentsInChildren_ChapterInfo___, method); /*0x1d53628*/
    sub_1B622A0(&UnityEngine_Debug_TypeInfo, v3); /*0x1d53634*/
    sub_1B622A0(&Method_System_Linq_Enumerable_First_Chapter___, v4); /*0x1d53640*/
    sub_1B622A0(&Method_System_Linq_Enumerable_Select_ChapterSongItem__string___, v5); /*0x1d5364c*/
    sub_1B622A0(&Method_System_Linq_Enumerable_ToArray_string___, v6); /*0x1d53658*/
    sub_1B622A0(&System_Func_ChapterSongItem__string__TypeInfo, v7); /*0x1d53664*/
    sub_1B622A0(&System_Func_Chapter__bool__TypeInfo, v8); /*0x1d53670*/
    sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, v9); /*0x1d5367c*/
    sub_1B622A0(&Method_System_Collections_Generic_List_ChapterSongItem__get_Count__, v10); /*0x1d53688*/
    sub_1B622A0(&Method_System_Collections_Generic_List_LevelRecord__get_Count__, v11); /*0x1d53694*/
    sub_1B622A0(&Method_System_Collections_Generic_List_LevelRecord__get_Item__, v12); /*0x1d536a0*/
    sub_1B622A0(&Method_System_Collections_Generic_List_ChapterSongItem__get_Item__, v13); /*0x1d536ac*/
    sub_1B622A0(&MultiLanguageTextBase_TypeInfo, v14); /*0x1d536b8*/
    sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Process_TypeInfo, v15); /*0x1d536c4*/
    sub_1B622A0(&SaveManagement_TypeInfo, v16); /*0x1d536d0*/
    sub_1B622A0(&Method_ChapterSelector___c__Start_b__18_3__, v17); /*0x1d536dc*/
    sub_1B622A0(&Method_ChapterSelector___c__Start_b__18_4__, v18); /*0x1d536e8*/
    sub_1B622A0(&Method_ChapterSelector___c__Start_b__18_5__, v19); /*0x1d536f4*/
    sub_1B622A0(&Method_ChapterSelector___c__Start_b__18_6__, v20); /*0x1d53700*/
    sub_1B622A0(&Method_ChapterSelector___c__DisplayClass18_0__Start_b__1__, v21); /*0x1d5370c*/
    sub_1B622A0(&Method_ChapterSelector___c__DisplayClass18_0__Start_b__2__, v22); /*0x1d53718*/
    sub_1B622A0(&ChapterSelector___c__DisplayClass18_0_TypeInfo, v23); /*0x1d53724*/
    sub_1B622A0(&ChapterSelector___c_TypeInfo, v24); /*0x1d53730*/
    sub_1B622A0(&UnityEngine_Events_UnityAction_TypeInfo, v25); /*0x1d5373c*/
    sub_1B622A0(&"SideStory4UnlockTip", v26); /*0x1d53748*/
    sub_1B622A0(&"HD", v27); /*0x1d53754*/
    sub_1B622A0(&"B<size=24> </size>O<size=24> </size>O<size=24> </size>T", v28); /*0x1d53760*/
    sub_1B622A0(&"chapter8Passed", v29); /*0x1d5376c*/
    sub_1B622A0(&"LastChapterSelectorIndex", v30); /*0x1d53778*/
    sub_1B622A0(&"VRC8", v31); /*0x1d53784*/
    sub_1B622A0(&"Can't find any ChapterInfo.", v32); /*0x1d53790*/
    sub_1B622A0(&"MainStory8_0", v33); /*0x1d5379c*/
    sub_1B622A0(&"GameCompleted", v34); /*0x1d537a8*/
    sub_1B622A0(&"C8", v35); /*0x1d537b4*/
    sub_1B622A0(&"Deciphered", v36); /*0x1d537c0*/
    sub_1B622A0(&"finishLegacyChapter", v37); /*0x1d537cc*/
    sub_1B622A0(&"Unlocked", v38); /*0x1d537d8*/
    sub_1B622A0(&"ChapterUnlockTip92w", v39); /*0x1d537e4*/
    sub_1B622A0(&"NowLevel", v40); /*0x1d537f0*/
    sub_1B622A0(&"IN", v41); /*0x1d537fc*/
    sub_1B622A0(&"MainStory9", v42); /*0x1d53808*/
    sub_1B622A0(&"ChapterOpen", v43); /*0x1d53814*/
    sub_1B622A0(&"MainStory8", v44); /*0x1d53820*/
    sub_1B622A0(&"MainStory8_1", v45); /*0x1d5382c*/
    sub_1B622A0(&"IsSimulateMode", v46); /*0x1d53838*/
    sub_1B622A0(&"EZ", v47); /*0x1d53844*/
    sub_1B622A0(&"ReadSideStory4Begin", v48); /*0x1d53850*/
    sub_1B622A0(&"MainStory8_2", v49); /*0x1d5385c*/
    sub_1B622A0(&"Chapter9Phase2Passed", v50); /*0x1d53868*/
    sub_1B622A0(&"LastChapterSelectorPos", v51); /*0x1d53874*/
    sub_1B622A0(&"", v52); /*0x1d53880*/
    sub_1B622A0(&"chapter8UnlockSecondPhase", v53); /*0x1d5388c*/
    sub_1B622A0(&"MainStoryChapterUnlockTip", v54); /*0x1d53898*/
    sub_1B622A0(&"MainStory9_2", v55); /*0x1d538a4*/
    byte_446E945 = 1; /*0x1d538ac*/
  }
  v338 = 0; /*0x1d538b0*/
  v339 = 0; /*0x1d538b0*/
  v337.fields.i = 0; /*0x1d538b4*/
  v336.fields.unlockFlag = 0; /*0x1d538b8*/
  chapterPanel = (__int64)this->fields.chapterPanel; /*0x1d538bc*/
  if ( !chapterPanel ) /*0x1d538c0*/
    goto LABEL_410; /*0x1d538c0*/
  childCount = UnityEngine_Transform__get_childCount((UnityEngine_Transform_o *)chapterPanel, 0); /*0x1d538e8*/
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d538ec*/
    j_il2cpp_runtime_class_init_0(v58, v59, v60); /*0x1d538f8*/
  String = SaveManagement__LoadString((System_String_o *)"GameCompleted", (System_String_o *)"", 0); /*0x1d53908*/
  chapterPanel = System_String__op_Equality(String, (System_String_o *)"", 0); /*0x1d53914*/
  chapterPanel_1 = this->fields.chapterPanel; /*0x1d53918*/
  if ( !chapterPanel_1 ) /*0x1d5391c*/
    goto LABEL_410; /*0x1d5391c*/
  v63 = childCount - (chapterPanel & 1); /*0x1d5392c*/
  value.fields.y = UnityEngine_RectTransform__get_sizeDelta(this->fields.chapterPanel, 0).fields.y; /*0x1d53930*/
  value.fields.x = (float)(295 * v63 + 790); /*0x1d53940*/
  UnityEngine_RectTransform__set_sizeDelta(chapterPanel_1, value, 0); /*0x1d5394c*/
  chapterPanel = (__int64)this->fields.chapterPanel; /*0x1d53950*/
  if ( !chapterPanel ) /*0x1d53954*/
    goto LABEL_410; /*0x1d53954*/
  chapterPanel = (__int64)UnityEngine_Component__GetComponentsInChildren_object__32798376( /*0x1d53964*/
                            (UnityEngine_Component_o *)chapterPanel,
                            (const MethodInfo_1F476A8 *)Method_UnityEngine_Component_GetComponentsInChildren_ChapterInfo___);
  this->fields._chapterInfos = (struct ChapterInfo_array *)chapterPanel; /*0x1d5396c*/
  p_chapterInfos = &this->fields._chapterInfos; /*0x1d5396c*/
  if ( chapterPanel && *(_QWORD *)(chapterPanel + 24) ) /*0x1d5397c*/
  {
    v69 = 0; /*0x1d5398c*/
    n4 = 4; /*0x1d53990*/
    while ( 1 ) /*0x1d53994*/
    {
      v71 = *(_DWORD *)(chapterPanel + 24); /*0x1d53994*/
      v72 = n4 - 4; /*0x1d53998*/
      if ( (int)n4 - 4 >= v71 ) /*0x1d539a0*/
        break; /*0x1d539a0*/
      if ( v72 >= v71 ) /*0x1d539a8*/
        goto LABEL_454; /*0x1d539a8*/
      v73 = *(_QWORD *)(chapterPanel + 8 * n4); /*0x1d539ac*/
      if ( !v73 ) /*0x1d539b0*/
        goto LABEL_410; /*0x1d539b0*/
      if ( *(_QWORD *)(v73 + 32) ) /*0x1d539b4*/
      {
        object = sub_1B624E8(ChapterSelector___c__DisplayClass18_0_TypeInfo); /*0x1d539d0*/
        System_Object___ctor((Il2CppObject *)object, 0); /*0x1d539d4*/
        if ( !object ) /*0x1d539d8*/
          goto LABEL_410; /*0x1d539d8*/
        *(_QWORD *)(object + 32) = this; /*0x1d539dc*/
        if ( !byte_446E372 ) /*0x1d539e8*/
        {
          chapterPanel = sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d539f4*/
          byte_446E372 = 1; /*0x1d53a00*/
        }
        chapterInfos_1 = *p_chapterInfos; /*0x1d53a04*/
        if ( !*p_chapterInfos ) /*0x1d53a04*/
          goto LABEL_410; /*0x1d53a08*/
        if ( v72 >= LODWORD(chapterInfos_1->max_length) ) /*0x1d53a14*/
          goto LABEL_454; /*0x1d53a14*/
        v76 = *((_QWORD *)&chapterInfos_1->obj.klass + n4); /*0x1d53a18*/
        if ( !v76 ) /*0x1d53a1c*/
          goto LABEL_410; /*0x1d53a1c*/
        chapterPanel = (__int64)GameInformation_TypeInfo->static_fields->_main; /*0x1d53a30*/
        if ( !chapterPanel ) /*0x1d53a34*/
          goto LABEL_410; /*0x1d53a34*/
        chapterPanel = (__int64)GameInformation__GetChapterWithCode( /*0x1d53a40*/
                                  (GameInformation_o *)chapterPanel,
                                  *(System_String_o **)(v76 + 32),
                                  0);
        chapterInfos_2 = *p_chapterInfos; /*0x1d53a44*/
        if ( !*p_chapterInfos ) /*0x1d53a44*/
          goto LABEL_410; /*0x1d53a48*/
        if ( v72 >= LODWORD(chapterInfos_2->max_length) ) /*0x1d53a54*/
          goto LABEL_454; /*0x1d53a54*/
        v78 = *((_QWORD *)&chapterInfos_2->obj.klass + n4); /*0x1d53a58*/
        if ( !v78 ) /*0x1d53a5c*/
          goto LABEL_410; /*0x1d53a5c*/
        v79 = *(_QWORD *)(v78 + 32); /*0x1d53a60*/
        *(_DWORD *)(object + 16) = v72; /*0x1d53a64*/
        *(_QWORD *)(object + 24) = v79; /*0x1d53a68*/
        v80 = *((_QWORD *)&chapterInfos_2->obj.klass + n4); /*0x1d53a6c*/
        if ( !v80 ) /*0x1d53a70*/
          goto LABEL_410; /*0x1d53a70*/
        v81 = *(_QWORD *)(v80 + 184); /*0x1d53a74*/
        if ( !v81 ) /*0x1d53a78*/
          goto LABEL_410; /*0x1d53a78*/
        v82 = *(UnityEngine_Events_UnityEvent_o **)(v81 + 256); /*0x1d53a7c*/
        chapterPanel_2 = chapterPanel; /*0x1d53a88*/
        call = (UnityEngine_Events_UnityAction_o *)sub_1B624E8(UnityEngine_Events_UnityAction_TypeInfo); /*0x1d53aa4*/
        UnityEngine_Events_UnityAction___ctor( /*0x1d53aac*/
          call,
          (Il2CppObject *)object,
          Method_ChapterSelector___c__DisplayClass18_0__Start_b__1__,
          0);
        if ( !v82 ) /*0x1d53ab0*/
          goto LABEL_410; /*0x1d53ab0*/
        UnityEngine_Events_UnityEvent__AddListener(v82, call, 0); /*0x1d53ac0*/
        if ( chapterPanel_2 ) /*0x1d53ac4*/
        {
          chapterInfos_3 = *p_chapterInfos; /*0x1d53ac8*/
          if ( !*p_chapterInfos ) /*0x1d53acc*/
            goto LABEL_410; /*0x1d53acc*/
          if ( v72 >= LODWORD(chapterInfos_3->max_length) ) /*0x1d53ad8*/
            goto LABEL_454; /*0x1d53ad8*/
          v86 = *((_QWORD *)&chapterInfos_3->obj.klass + n4); /*0x1d53adc*/
          if ( !v86 ) /*0x1d53ae0*/
            goto LABEL_410; /*0x1d53ae0*/
          v87 = *(_QWORD *)(chapterPanel_2 + 24); /*0x1d53ae4*/
          if ( v87 ) /*0x1d53aec*/
            v69 = *(_QWORD *)(chapterPanel_2 + 24); /*0x1d53aec*/
          *(_QWORD *)(v86 + 40) = v87; /*0x1d53af0*/
          if ( v87 ) /*0x1d53af4*/
          {
            if ( !v69 ) /*0x1d53af8*/
              goto LABEL_410; /*0x1d53af8*/
            if ( *(_QWORD *)(v69 + 16) ) /*0x1d53afc*/
            {
              chapterPanel = System_String__op_Inequality( /*0x1d53b10*/
                               *(System_String_o **)(v87 + 16),
                               (System_String_o *)"",
                               0);
              if ( (chapterPanel & 1) != 0 ) /*0x1d53b14*/
              {
                chapterInfos_4 = *p_chapterInfos; /*0x1d53b18*/
                if ( !*p_chapterInfos ) /*0x1d53b1c*/
                  goto LABEL_410; /*0x1d53b1c*/
                if ( v72 >= LODWORD(chapterInfos_4->max_length) ) /*0x1d53b28*/
                  goto LABEL_454; /*0x1d53b28*/
                v89 = *((_QWORD *)&chapterInfos_4->obj.klass + n4); /*0x1d53b2c*/
                if ( !v89 ) /*0x1d53b30*/
                  goto LABEL_410; /*0x1d53b30*/
                v90 = *(_QWORD *)(chapterPanel_2 + 24); /*0x1d53b34*/
                if ( !v90 ) /*0x1d53b38*/
                  goto LABEL_410; /*0x1d53b38*/
                chapterPanel = *(_QWORD *)(v89 + 136); /*0x1d53b3c*/
                if ( !chapterPanel ) /*0x1d53b40*/
                  goto LABEL_410; /*0x1d53b40*/
                chapterPanel = (*(__int64 (__fastcall **)(__int64, _QWORD, _QWORD))(*(_QWORD *)chapterPanel + 1512LL))( /*0x1d53b54*/
                                 chapterPanel,
                                 *(_QWORD *)(v90 + 16),
                                 *(_QWORD *)(*(_QWORD *)chapterPanel + 1520LL));
                chapterInfos_5 = *p_chapterInfos; /*0x1d53b58*/
                if ( !*p_chapterInfos ) /*0x1d53b58*/
                  goto LABEL_410; /*0x1d53b5c*/
                if ( v72 >= LODWORD(chapterInfos_5->max_length) ) /*0x1d53b68*/
                  goto LABEL_454; /*0x1d53b68*/
                v92 = *((_QWORD *)&chapterInfos_5->obj.klass + n4); /*0x1d53b6c*/
                if ( !v92 ) /*0x1d53b70*/
                  goto LABEL_410; /*0x1d53b70*/
                v93 = *(_QWORD *)(chapterPanel_2 + 24); /*0x1d53b74*/
                if ( !v93 ) /*0x1d53b78*/
                  goto LABEL_410; /*0x1d53b78*/
                chapterPanel = *(_QWORD *)(v92 + 144); /*0x1d53b7c*/
                if ( !chapterPanel ) /*0x1d53b80*/
                  goto LABEL_410; /*0x1d53b80*/
                chapterPanel = (*(__int64 (__fastcall **)(__int64, _QWORD, _QWORD))(*(_QWORD *)chapterPanel + 1512LL))( /*0x1d53b94*/
                                 chapterPanel,
                                 *(_QWORD *)(v93 + 24),
                                 *(_QWORD *)(*(_QWORD *)chapterPanel + 1520LL));
                chapterInfos_6 = *p_chapterInfos; /*0x1d53b98*/
                if ( !*p_chapterInfos ) /*0x1d53b98*/
                  goto LABEL_410; /*0x1d53b9c*/
                if ( v72 >= LODWORD(chapterInfos_6->max_length) ) /*0x1d53ba8*/
                  goto LABEL_454; /*0x1d53ba8*/
                v95 = *((_QWORD *)&chapterInfos_6->obj.klass + n4); /*0x1d53bac*/
                if ( !v95 ) /*0x1d53bb0*/
                  goto LABEL_410; /*0x1d53bb0*/
                v96 = *(_QWORD *)(chapterPanel_2 + 24); /*0x1d53bb4*/
                if ( !v96 ) /*0x1d53bb8*/
                  goto LABEL_410; /*0x1d53bb8*/
                chapterPanel = *(_QWORD *)(v95 + 128); /*0x1d53bbc*/
                if ( !chapterPanel ) /*0x1d53bc0*/
                  goto LABEL_410; /*0x1d53bc0*/
                chapterPanel = (*(__int64 (__fastcall **)(__int64, _QWORD, _QWORD))(*(_QWORD *)chapterPanel + 1512LL))( /*0x1d53bd4*/
                                 chapterPanel,
                                 *(_QWORD *)(v96 + 32),
                                 *(_QWORD *)(*(_QWORD *)chapterPanel + 1520LL));
                v97 = *(_QWORD *)(chapterPanel_2 + 32); /*0x1d53bd8*/
                if ( v97 ) /*0x1d53bdc*/
                {
                  chapterInfos_7 = *p_chapterInfos; /*0x1d53be0*/
                  if ( !*p_chapterInfos ) /*0x1d53be4*/
                    goto LABEL_410; /*0x1d53be4*/
                  if ( v72 >= LODWORD(chapterInfos_7->max_length) ) /*0x1d53bf0*/
                    goto LABEL_454; /*0x1d53bf0*/
                  v99 = *((_QWORD *)&chapterInfos_7->obj.klass + n4); /*0x1d53bf4*/
                  if ( !v99 ) /*0x1d53bf8*/
                    goto LABEL_410; /*0x1d53bf8*/
                  *(_QWORD *)(v99 + 48) = v97; /*0x1d53bfc*/
                  v100 = *((_QWORD *)&chapterInfos_7->obj.klass + n4); /*0x1d53c04*/
                  chapterPanel = (__int64)IdBuilder__BuildBackgroundImageId(*(System_String_o **)(object + 24), 0); /*0x1d53c0c*/
                  if ( !v100 ) /*0x1d53c10*/
                    goto LABEL_410; /*0x1d53c10*/
                  *(_QWORD *)(v100 + 56) = chapterPanel; /*0x1d53c14*/
                  chapterInfos_8 = *p_chapterInfos; /*0x1d53c18*/
                  if ( !*p_chapterInfos ) /*0x1d53c18*/
                    goto LABEL_410; /*0x1d53c1c*/
                  if ( v72 >= LODWORD(chapterInfos_8->max_length) ) /*0x1d53c28*/
                    goto LABEL_454; /*0x1d53c28*/
                  v102 = *((_QWORD *)&chapterInfos_8->obj.klass + n4); /*0x1d53c30*/
                  chapterPanel = (__int64)IdBuilder__BuildBlurBackgroundImageId(*(System_String_o **)(object + 24), 0); /*0x1d53c38*/
                  if ( !v102 ) /*0x1d53c3c*/
                    goto LABEL_410; /*0x1d53c3c*/
                  *(_QWORD *)(v102 + 64) = chapterPanel; /*0x1d53c40*/
                  v103 = *(_QWORD *)(chapterPanel_2 + 32); /*0x1d53c44*/
                  if ( !v103 ) /*0x1d53c48*/
                    goto LABEL_410; /*0x1d53c48*/
                  str0 = *(System_String_o **)(v103 + 40); /*0x1d53c4c*/
                  if ( System_String__op_Inequality(str0, (System_String_o *)"", 0) ) /*0x1d53c5c*/
                  {
                    keyName_2 = System_String__Concat_53436140(str0, (System_String_o *)"EZ", 0); /*0x1d53c8c*/
                    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d53c94*/
                      j_il2cpp_runtime_class_init_0(v106, v107, v108); /*0x1d53ca0*/
                    if ( !SaveManagement__LoadBool(keyName_2, 0, 0) ) /*0x1d53cb4*/
                    {
                      keyName_1 = System_String__Concat_53436140(str0, (System_String_o *)"HD", 0); /*0x1d53cd8*/
                      if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d53ce0*/
                        j_il2cpp_runtime_class_init_0(v110, v111, v112); /*0x1d53cec*/
                      if ( !SaveManagement__LoadBool(keyName_1, 0, 0) ) /*0x1d53d00*/
                      {
                        keyName = System_String__Concat_53436140(str0, (System_String_o *)"IN", 0); /*0x1d53d24*/
                        if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d53d2c*/
                          j_il2cpp_runtime_class_init_0(v114, v115, v116); /*0x1d53d38*/
                        chapterPanel = SaveManagement__LoadBool(keyName, 0, 0); /*0x1d53d48*/
                        if ( (chapterPanel & 1) == 0 ) /*0x1d53d4c*/
                        {
                          chapterInfos_9 = *p_chapterInfos; /*0x1d53d50*/
                          if ( !*p_chapterInfos ) /*0x1d53d54*/
                            goto LABEL_410; /*0x1d53d54*/
                          if ( v72 >= LODWORD(chapterInfos_9->max_length) ) /*0x1d53d60*/
                            goto LABEL_454; /*0x1d53d60*/
                          v118 = *(_QWORD *)(chapterPanel_2 + 32); /*0x1d53d64*/
                          if ( !v118 ) /*0x1d53d68*/
                            goto LABEL_410; /*0x1d53d68*/
                          v119 = *((_QWORD *)&chapterInfos_9->obj.klass + n4); /*0x1d53d6c*/
                          if ( !v119 ) /*0x1d53d70*/
                            goto LABEL_410; /*0x1d53d70*/
                          *(_QWORD *)(v119 + 56) = *(_QWORD *)(v118 + 48); /*0x1d53d78*/
                          v120 = *((_QWORD *)&chapterInfos_9->obj.klass + n4); /*0x1d53d7c*/
                          if ( !v120 ) /*0x1d53d80*/