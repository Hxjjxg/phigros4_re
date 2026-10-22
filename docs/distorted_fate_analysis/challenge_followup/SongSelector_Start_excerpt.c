  SongSelector___c__DisplayClass149_0___ctor(CS___8__locals1_1, 0); /*0x1da248c*/
  if ( !CS___8__locals1_1 ) /*0x1da2490*/
    goto LABEL_242; /*0x1da2490*/
  p_SaveManagement_TypeInfo = &SaveManagement_TypeInfo; /*0x1da2498*/
  CS___8__locals1_1->fields.__4__this = this; /*0x1da249c*/
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1da24b0*/
    j_il2cpp_runtime_class_init_0(v71, v72, v73); /*0x1da24c4*/
  String = SaveManagement__LoadString((System_String_o *)StringLiteral_4780, (System_String_o *)StringLiteral_1, 0); /*0x1da24e8*/
  this->fields.enableLegacy = System_String__op_Inequality(String, (System_String_o *)StringLiteral_1, 0); /*0x1da24fc*/
  this->fields.enableChartMirror = SaveManagement__LoadBool((System_String_o *)StringLiteral_11923, 0, 0); /*0x1da2514*/
  if ( !byte_446E372 ) /*0x1da2520*/
  {
    sub_1B622A0(&GameInformation_TypeInfo, nowLevel); /*0x1da252c*/
    byte_446E372 = 1; /*0x1da2534*/
  }
  main = (__int64)GameInformation_TypeInfo->static_fields->_main; /*0x1da2540*/
  if ( !main ) /*0x1da2544*/
    goto LABEL_242; /*0x1da2544*/
  this->fields.nowChapterInfo = GameInformation__GetNowChapter((GameInformation_o *)main, 0); /*0x1da2558*/
  main = System_String__op_Inequality( /*0x1da2570*/
           GameInformation_TypeInfo->static_fields->nowChapterCode,
           (System_String_o *)StringLiteral_3139,
           0);
  if ( (main & 1) != 0 /*0x1da258c*/
    && (main = Phigros2_Chapter9_Key__IsCollectionRead((System_String_o *)StringLiteral_14210, 0), (main & 1) != 0) )
  {
    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1da2594*/
      j_il2cpp_runtime_class_init_0(v76, v77, v78); /*0x1da259c*/
    main = SaveManagement__LoadBool((System_String_o *)StringLiteral_2576, 0, 0); /*0x1da25b4*/
    isC9ImplexroughRandomEventActive = (main & 1) == 0; /*0x1da25bc*/
  }
  else
  {
    isC9ImplexroughRandomEventActive = 0; /*0x1da25c4*/
  }
  this->fields.isC9ImplexroughRandomEventActive = isC9ImplexroughRandomEventActive; /*0x1da25c8*/
  if ( isC9ImplexroughRandomEventActive ) /*0x1da25cc*/
  {
    this->fields.c9RandomPityTarget = Phigros2_Utils_TrueRandom__RandomInt_30277736(10, 21, 0); /*0x1da25e8*/
    *(_WORD *)&this->fields.c9RandomSelectionAwaitingDecision = 0; /*0x1da25f4*/
    *(_QWORD *)&this->fields.c9RandomPityCount = 0xFFFFFFFF00000000LL; /*0x1da25f8*/
    this->fields.c9RandomSelectionReached = 0; /*0x1da25fc*/
    SongSelector__BeginLoadImplexroughRandomGlitchAudio(this, method_1); /*0x1da2600*/
    imageId = IdBuilder__BuildCoverImageId((System_String_o *)StringLiteral_5218, (System_String_o *)StringLiteral_1, 0); /*0x1da2618*/
    SongSelector__PreloadImage(this, imageId, method_2); /*0x1da2624*/
    imageId_1 = IdBuilder__BuildBlurImageId( /*0x1da2634*/
                  (System_String_o *)StringLiteral_5218,
                  (System_String_o *)StringLiteral_1,
                  0);
    SongSelector__PreloadImage(this, imageId_1, method_3); /*0x1da2640*/
  }
  levelSliderController = this->fields.levelSliderController; /*0x1da2644*/
  if ( !levelSliderController ) /*0x1da2648*/
    goto LABEL_242; /*0x1da2648*/
  levelSliderController->fields.levelColor = this->fields.levelColor; /*0x1da2654*/
  main = System_String__op_Equality( /*0x1da2668*/
           GameInformation_TypeInfo->static_fields->nowChapterCode,
           (System_String_o *)StringLiteral_3139,
           0);
  if ( (main & 1) != 0 ) /*0x1da266c*/
  {
    normalComponents = this->fields.normalComponents; /*0x1da2670*/
    if ( !normalComponents ) /*0x1da2674*/
      goto LABEL_242; /*0x1da2674*/
    max_length = normalComponents->max_length; /*0x1da2678*/
    if ( max_length >= 1 ) /*0x1da2680*/
    {
      max_length_1 = 0; /*0x1da2684*/
      while ( max_length_1 < max_length ) /*0x1da268c*/
      {
        main = (__int64)normalComponents->m_Items[max_length_1]; /*0x1da2694*/
        if ( !main ) /*0x1da2698*/
          goto LABEL_242; /*0x1da2698*/
        UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)main, 0, 0); /*0x1da26a4*/
        max_length = normalComponents->max_length; /*0x1da26a8*/
        if ( (int)++max_length_1 >= max_length ) /*0x1da26b4*/
          goto LABEL_25; /*0x1da26b4*/
      }
      goto LABEL_243; /*0x1da268c*/
    }
LABEL_25:
    challengeModeComponents = this->fields.challengeModeComponents; /*0x1da26b8*/
    if ( !challengeModeComponents ) /*0x1da26bc*/
      goto LABEL_242; /*0x1da26bc*/
    max_length_2 = challengeModeComponents->max_length; /*0x1da26c0*/
    if ( max_length_2 >= 1 ) /*0x1da26c8*/
    {
      max_length_3 = 0; /*0x1da26cc*/
      while ( max_length_3 < max_length_2 ) /*0x1da26d4*/
      {
        main = (__int64)challengeModeComponents->m_Items[max_length_3]; /*0x1da26dc*/
        if ( !main ) /*0x1da26e0*/
          goto LABEL_242; /*0x1da26e0*/
        UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)main, 1, 0); /*0x1da26ec*/
        max_length_2 = challengeModeComponents->max_length; /*0x1da26f0*/
        if ( (int)++max_length_3 >= max_length_2 ) /*0x1da26fc*/
          goto LABEL_31; /*0x1da26fc*/
      }
      goto LABEL_243; /*0x1da26d4*/
    }
LABEL_31:
    challengeSongSelectorControl = this->fields.challengeSongSelectorControl; /*0x1da2700*/
    if ( !challengeSongSelectorControl ) /*0x1da2704*/
      goto LABEL_242; /*0x1da2704*/
    challengeSongSelectorControl->fields.songSelector = this; /*0x1da2708*/
  }
  else
  {
    challengeModeComponents_1 = this->fields.challengeModeComponents; /*0x1da2714*/
    if ( !challengeModeComponents_1 ) /*0x1da2718*/
