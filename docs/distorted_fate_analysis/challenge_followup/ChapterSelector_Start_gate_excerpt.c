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
    sub_1B622A0(&StringLiteral_7972, v26); /*0x1d53748*/
    sub_1B622A0(&StringLiteral_4904, v27); /*0x1d53754*/
    sub_1B622A0(&StringLiteral_2340, v28); /*0x1d53760*/
    sub_1B622A0(&StringLiteral_11900, v29); /*0x1d5376c*/
    sub_1B622A0(&StringLiteral_5990, v30); /*0x1d53778*/
    sub_1B622A0(&StringLiteral_10045, v31); /*0x1d53784*/
    sub_1B622A0(&StringLiteral_2726, v32); /*0x1d53790*/
    sub_1B622A0(&StringLiteral_6255, v33); /*0x1d5379c*/
    sub_1B622A0(&StringLiteral_4780, v34); /*0x1d537a8*/
    sub_1B622A0(&StringLiteral_2560, v35); /*0x1d537b4*/
    sub_1B622A0(&StringLiteral_3860, v36); /*0x1d537c0*/
    sub_1B622A0(&StringLiteral_12716, v37); /*0x1d537cc*/
    sub_1B622A0(&StringLiteral_9940, v38); /*0x1d537d8*/
    sub_1B622A0(&StringLiteral_3226, v39); /*0x1d537e4*/
    sub_1B622A0(&StringLiteral_6800, v40); /*0x1d537f0*/
    sub_1B622A0(&StringLiteral_5129, v41); /*0x1d537fc*/
    sub_1B622A0(&StringLiteral_6264, v42); /*0x1d53808*/
    sub_1B622A0(&StringLiteral_3207, v43); /*0x1d53814*/
    sub_1B622A0(&StringLiteral_6254, v44); /*0x1d53820*/
    sub_1B622A0(&StringLiteral_6258, v45); /*0x1d5382c*/
    sub_1B622A0(&StringLiteral_5777, v46); /*0x1d53838*/
    sub_1B622A0(&StringLiteral_4093, v47); /*0x1d53844*/
    sub_1B622A0(&StringLiteral_7482, v48); /*0x1d53850*/
    sub_1B622A0(&StringLiteral_6261, v49); /*0x1d5385c*/
    sub_1B622A0(&StringLiteral_3169, v50); /*0x1d53868*/
    sub_1B622A0(&StringLiteral_5991, v51); /*0x1d53874*/
    sub_1B622A0(&StringLiteral_1, v52); /*0x1d53880*/
    sub_1B622A0(&StringLiteral_11903, v53); /*0x1d5388c*/
    sub_1B622A0(&StringLiteral_6267, v54); /*0x1d53898*/
    sub_1B622A0(&StringLiteral_6266, v55); /*0x1d538a4*/
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
  String = SaveManagement__LoadString((System_String_o *)StringLiteral_4780, (System_String_o *)StringLiteral_1, 0); /*0x1d53908*/
  chapterPanel = System_String__op_Equality(String, (System_String_o *)StringLiteral_1, 0); /*0x1d53914*/
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
