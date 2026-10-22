void Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl__Start(
        Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *this,
        const MethodInfo *method)
{
  Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *this_1; // x19
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
  AddressableAudioSource_o *audioSource; // x21
  struct GameInformation_o *main; // x8
  struct LevelStartInfo_o *levelStartInfo; // x8
  System_String_o *songsLevel; // x22
  System_String_o **p_eZ; // x8
  struct GameInformation_o *_main; // x8
  int v21; // w29
  int32_t index; // w21
  Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *this_2; // x22
  struct GameInformation_o *_main_1; // x8
  intptr_t m_CachedPtr; // x8
  Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *this_3; // x23
  __int64 v27; // x28
  __int64 v28; // x2
  __int64 v29; // x8
  __int64 m_CancellationTokenSource_low; // x9
  __int64 v31; // x8
  __int64 v32; // x9
  float v33; // s0
  struct System_String_o *eZ; // x24
  int v35; // w8
  System_String_o *str1; // x0
  __int64 v37; // x8
  __int64 v38; // x9
  float v39; // s0
  int v40; // w9
  struct System_String_o *eZ_1; // x8
  struct System_String_o *hD; // x24
  struct System_String_o *eZ_2; // x8
  struct System_String_o *iN; // x24
  struct System_String_o *eZ_3; // x8
  struct System_String_o *aT; // x24
  struct System_String_o *eZ_4; // x8
  struct UnityEngine_GameObject_array *rankBackgrounds; // x24
  struct System_String_o *eZ_5; // x8
  struct UnityEngine_UI_Text_o *rankNumText; // x24
  struct System_String_o *eZ_6; // x8
  int32_t stringLength; // w20
  struct UnityEngine_Animator_o *selectRankWindow; // x24
  struct System_String_o *eZ_7; // x8
  __int64 v55; // x24
  intptr_t m_CachedPtr_1; // x8
  struct UnityEngine_UI_Text_o *score; // x21
  const MethodInfo *method_1; // x2
  const MethodInfo *method_2; // x3
  float v60; // s0
  float v61; // s1
  float v62; // s2
  Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_c *Phigros2.ChallengeMode.Scripts.ChallengeModeOverControl_TypeInf; // x0
  const MethodInfo *method_3; // x1
  float v65; // s0
  float v66; // s1
  float v67; // s2
  GameInformation_o *_main_2; // x21
  int n5; // w8
  AddressableImageLoader_o *background; // x21
  System_String_o *defaultValue; // x22
  float v72; // s0
  float v73; // s1
  float v74; // s2
  AddressableImageLoader_o *background2; // x21
  System_String_o *defaultValue_1; // x0
  struct GameInformation_o *_main_3; // x20
  const MethodInfo *method_4; // x2
  _BOOL4 secretChallengeLifeFailed; // [xsp+4h] [xbp-7Ch]
  __int64 v80; // [xsp+8h] [xbp-78h] BYREF
  Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl___c__DisplayClass27_0_o v81; // [xsp+10h] [xbp-70h] BYREF

  this_1 = this; /*0x1d210a0*/
  if ( (byte_446E79E & 1) == 0 ) /*0x1d210a4*/
  {
    sub_1B622A0(&Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_TypeInfo, method); /*0x1d210b0*/
    sub_1B622A0(&Method_System_Collections_Generic_List_ChallengeModeOverControl_LevelOverComponent__get_Item__, v3); /*0x1d210bc*/
    sub_1B622A0(&Method_System_Collections_Generic_List_ChallengeSongItem__get_Item__, v4); /*0x1d210c8*/
    sub_1B622A0(&SaveManagement_TypeInfo, v5); /*0x1d210d4*/
    sub_1B622A0(&Phigros2_ChallengeMode_Scripts_SecretChallengeLifeRules_TypeInfo, v6); /*0x1d210e0*/
    sub_1B622A0(&"HD", v7); /*0x1d210ec*/
    sub_1B622A0(&"AT", v8); /*0x1d210f8*/
    sub_1B622A0(&"LastChapterBackgroundCode", v9); /*0x1d21104*/
    sub_1B622A0(&"D7", v10); /*0x1d21110*/
    sub_1B622A0(&"IN", v11); /*0x1d2111c*/
    sub_1B622A0(&"Lv.", v12); /*0x1d21128*/
    sub_1B622A0(&"EZ", v13); /*0x1d21134*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)sub_1B622A0(&"MainStoryLegacy", v14); /*0x1d21140*/
    byte_446E79E = 1; /*0x1d21148*/
  }
  v80 = 0; /*0x1d2114c*/
  v81.fields.__4__this = this_1; /*0x1d2114c*/
  v81.fields.gameInformation = 0; /*0x1d21150*/
  audioSource = this_1->fields.audioSource; /*0x1d2115c*/
  if ( !byte_446E372 ) /*0x1d21160*/
  {
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d2116c*/
    byte_446E372 = 1; /*0x1d21174*/
  }
  main = GameInformation_TypeInfo->static_fields->_main; /*0x1d21188*/
  if ( !main ) /*0x1d2118c*/
    goto LABEL_91; /*0x1d2118c*/
  levelStartInfo = main->fields.levelStartInfo; /*0x1d21190*/
  if ( !levelStartInfo ) /*0x1d21194*/
    goto LABEL_91; /*0x1d21194*/
  songsLevel = levelStartInfo->fields.songsLevel; /*0x1d211a0*/
  this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_String__op_Equality( /*0x1d211b0*/
                                                                        songsLevel,
                                                                        (System_String_o *)"EZ",
                                                                        0);
  if ( ((unsigned __int8)this & 1) != 0 ) /*0x1d211b4*/
  {
    p_eZ = &this_1->fields.eZ; /*0x1d211b8*/
  }
  else
  {
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_String__op_Equality( /*0x1d211d4*/
                                                                          songsLevel,
                                                                          (System_String_o *)"HD",
                                                                          0);
    if ( ((unsigned __int8)this & 1) != 0 ) /*0x1d211d8*/
    {
      p_eZ = &this_1->fields.hD; /*0x1d211dc*/
    }
    else
    {
      this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_String__op_Equality( /*0x1d211f8*/
                                                                            songsLevel,
                                                                            (System_String_o *)"IN",
                                                                            0);
      if ( ((unsigned __int8)this & 1) != 0 /*0x1d21218*/
        || (this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_String__op_Equality(
                                                                                  songsLevel,
                                                                                  (System_String_o *)"AT",
                                                                                  0),
            ((unsigned __int8)this & 1) == 0) )
      {
        p_eZ = &this_1->fields.iN; /*0x1d21224*/
      }
      else
      {
        p_eZ = &this_1->fields.aT; /*0x1d2121c*/
      }
    }
  }
  if ( !audioSource ) /*0x1d21228*/
    goto LABEL_91; /*0x1d21228*/
  AddressableAudioSource__set_Clip(audioSource, *p_eZ, 0); /*0x1d21238*/
  this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)this_1->fields.audioSource; /*0x1d2123c*/
  if ( !this ) /*0x1d21240*/
    goto LABEL_91; /*0x1d21240*/
  AddressableAudioSource__Play((AddressableAudioSource_o *)this, 0); /*0x1d21248*/
  HIDWORD(v80) = 0; /*0x1d21250*/
  if ( !byte_446E372 ) /*0x1d21254*/
  {
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d21260*/
    byte_446E372 = 1; /*0x1d21268*/
  }
  _main = GameInformation_TypeInfo->static_fields->_main; /*0x1d2127c*/
  if ( !_main ) /*0x1d21280*/
    goto LABEL_91; /*0x1d21280*/
  v21 = 0; /*0x1d21288*/
  index = 0; /*0x1d2128c*/
  secretChallengeLifeFailed = _main->fields.secretChallengeLifeFailed; /*0x1d21298*/
  do /*0x1d21610*/
  {
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)this_1->fields.levelOverComponents; /*0x1d2129c*/
    if ( !this ) /*0x1d212a0*/
      goto LABEL_91; /*0x1d212a0*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_Collections_Generic_List_object___get_Item( /*0x1d212b4*/
                                                                          (System_Collections_Generic_List_object__o *)this,
                                                                          index,
                                                                          (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_ChallengeModeOverControl_LevelOverComponent__get_Item__);
    this_2 = this; /*0x1d212bc*/
    if ( !byte_446E372 ) /*0x1d212c0*/
    {
      this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d212cc*/
      byte_446E372 = 1; /*0x1d212d4*/
    }
    _main_1 = GameInformation_TypeInfo->static_fields->_main; /*0x1d212e8*/
    if ( !_main_1 ) /*0x1d212ec*/
      goto LABEL_91; /*0x1d212ec*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)_main_1->fields.challengeSongItems; /*0x1d212f0*/
    if ( !this ) /*0x1d212f4*/
      goto LABEL_91; /*0x1d212f4*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_Collections_Generic_List_object___get_Item( /*0x1d21308*/
                                                                          (System_Collections_Generic_List_object__o *)this,
                                                                          index,
                                                                          (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_ChallengeSongItem__get_Item__);
    if ( !this ) /*0x1d2130c*/
      goto LABEL_91; /*0x1d2130c*/
    m_CachedPtr = this->fields.m_CachedPtr; /*0x1d21310*/
    this_3 = this; /*0x1d21314*/
    if ( !m_CachedPtr ) /*0x1d21318*/
      goto LABEL_91; /*0x1d21318*/
    if ( !this_2 ) /*0x1d2131c*/
      goto LABEL_91; /*0x1d2131c*/
    v27 = *(_QWORD *)(m_CachedPtr + 16); /*0x1d21320*/
    if ( !v27 ) /*0x1d21324*/
      goto LABEL_91; /*0x1d21324*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)this_2->fields.m_CachedPtr; /*0x1d21328*/
    if ( !this ) /*0x1d2132c*/
      goto LABEL_91; /*0x1d2132c*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)((__int64 (__fastcall *)(Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *, _QWORD, const char *))this->klass[4]._1.gc_desc)( /*0x1d21340*/
                                                                          this,
                                                                          *(_QWORD *)(v27 + 32),
                                                                          this->klass[4]._1.name);
    v29 = *(_QWORD *)(v27 + 80); /*0x1d21344*/
    if ( !v29 ) /*0x1d21348*/
      goto LABEL_91; /*0x1d21348*/
    m_CancellationTokenSource_low = SLODWORD(this_3->fields.m_CancellationTokenSource); /*0x1d2134c*/
    if ( (unsigned int)m_CancellationTokenSource_low >= *(_DWORD *)(v29 + 24) ) /*0x1d21358*/
      goto LABEL_92; /*0x1d21358*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)this_2->fields.m_CancellationTokenSource; /*0x1d2135c*/
    if ( !this ) /*0x1d21360*/
      goto LABEL_91; /*0x1d21360*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)((__int64 (__fastcall *)(Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *, _QWORD, const char *))this->klass[4]._1.gc_desc)( /*0x1d21378*/
                                                                          this,
                                                                          *(_QWORD *)(v29
                                                                                    + 8 * m_CancellationTokenSource_low
                                                                                    + 32),
                                                                          this->klass[4]._1.name);
    v31 = *(_QWORD *)(v27 + 48); /*0x1d2137c*/
    if ( !v31 ) /*0x1d21380*/
      goto LABEL_91; /*0x1d21380*/
    v32 = SLODWORD(this_3->fields.m_CancellationTokenSource); /*0x1d21384*/
    if ( (unsigned int)v32 >= *(_DWORD *)(v31 + 24) ) /*0x1d21390*/
      goto LABEL_92; /*0x1d21390*/
    v33 = *(float *)(v31 + 4 * v32 + 32); /*0x1d21398*/
    eZ = this_2->fields.eZ; /*0x1d2139c*/
    v35 = (int)v33; /*0x1d213a8*/
    if ( v33 == INFINITY ) /*0x1d213b0*/
      v35 = 0x80000000; /*0x1d213b0*/
    LODWORD(v80) = v35; /*0x1d213b8*/
    str1 = System_Int32__ToString((int32_t)&v80, 0); /*0x1d213bc*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_String__Concat_53436140( /*0x1d213d8*/
                                                                          (System_String_o *)"Lv.",
                                                                          str1,
                                                                          0);
    if ( !eZ ) /*0x1d213dc*/
      goto LABEL_91; /*0x1d213dc*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)((__int64 (__fastcall *)(struct System_String_o *, Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *, void *))eZ->klass[2]._1.namespaze)( /*0x1d213f4*/
                                                                          eZ,
                                                                          this,
                                                                          eZ->klass[2]._1.byval_arg.data);
    v37 = *(_QWORD *)(v27 + 48); /*0x1d213f8*/
    if ( !v37 ) /*0x1d213fc*/
      goto LABEL_91; /*0x1d213fc*/
    v38 = SLODWORD(this_3->fields.m_CancellationTokenSource); /*0x1d21400*/
    if ( (unsigned int)v38 >= *(_DWORD *)(v37 + 24) ) /*0x1d2140c*/
LABEL_92:
      sub_1B62500(this, method, v28); /*0x1d218a0*/
    v39 = *(float *)(v37 + 4 * v38 + 32); /*0x1d21414*/
    v40 = (int)v39; /*0x1d21420*/
    if ( v39 == INFINITY ) /*0x1d21428*/
      v40 = 0x80000000; /*0x1d21428*/
    this_1->fields.rankNum += v40; /*0x1d21430*/
    eZ_1 = this_3->fields.eZ; /*0x1d21434*/
    if ( !eZ_1 ) /*0x1d21438*/
      goto LABEL_91; /*0x1d21438*/
    hD = this_2->fields.hD; /*0x1d21440*/
    LODWORD(v80) = eZ_1->fields._stringLength; /*0x1d21454*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_Int32__ToString_54991860( /*0x1d2145c*/
                                                                          (int32_t)&v80,
                                                                          (System_String_o *)"D7",
                                                                          0);
    if ( !hD ) /*0x1d21460*/
      goto LABEL_91; /*0x1d21460*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)((__int64 (__fastcall *)(struct System_String_o *, Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *, void *))hD->klass[2]._1.namespaze)( /*0x1d21478*/
                                                                          hD,
                                                                          this,
                                                                          hD->klass[2]._1.byval_arg.data);
    eZ_2 = this_3->fields.eZ; /*0x1d2147c*/
    if ( !eZ_2 ) /*0x1d21480*/
      goto LABEL_91; /*0x1d21480*/
    HIDWORD(v80) += eZ_2->fields._stringLength; /*0x1d21498*/
    iN = this_2->fields.iN; /*0x1d2149c*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_Int32__ToString((int)eZ_2 + 28, 0); /*0x1d214a0*/
    if ( !iN ) /*0x1d214a4*/
      goto LABEL_91; /*0x1d214a4*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)((__int64 (__fastcall *)(struct System_String_o *, Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *, void *))iN->klass[2]._1.namespaze)( /*0x1d214bc*/
                                                                          iN,
                                                                          this,
                                                                          iN->klass[2]._1.byval_arg.data);
    eZ_3 = this_3->fields.eZ; /*0x1d214c0*/
    if ( !eZ_3 ) /*0x1d214c4*/
      goto LABEL_91; /*0x1d214c4*/
    aT = this_2->fields.aT; /*0x1d214c8*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_Int32__ToString((int)eZ_3 + 32, 0); /*0x1d214d4*/
    if ( !aT ) /*0x1d214d8*/
      goto LABEL_91; /*0x1d214d8*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)((__int64 (__fastcall *)(struct System_String_o *, Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *, void *))aT->klass[2]._1.namespaze)( /*0x1d214f0*/
                                                                          aT,
                                                                          this,
                                                                          aT->klass[2]._1.byval_arg.data);
    eZ_4 = this_3->fields.eZ; /*0x1d214f4*/
    if ( !eZ_4 ) /*0x1d214f8*/
      goto LABEL_91; /*0x1d214f8*/
    rankBackgrounds = this_2->fields.rankBackgrounds; /*0x1d214fc*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_Int32__ToString((int)eZ_4 + 36, 0); /*0x1d21508*/
    if ( !rankBackgrounds ) /*0x1d2150c*/
      goto LABEL_91; /*0x1d2150c*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)((__int64 (__fastcall *)(struct UnityEngine_GameObject_array *, Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *, const MethodInfo *))rankBackgrounds->obj.klass->vtable[75].methodPtr)( /*0x1d21524*/
                                                                          rankBackgrounds,
                                                                          this,
                                                                          rankBackgrounds->obj.klass->vtable[75].method);
    eZ_5 = this_3->fields.eZ; /*0x1d21528*/
    if ( !eZ_5 ) /*0x1d2152c*/
      goto LABEL_91; /*0x1d2152c*/
    rankNumText = this_2->fields.rankNumText; /*0x1d21530*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_Int32__ToString((int)eZ_5 + 40, 0); /*0x1d2153c*/
    if ( !rankNumText ) /*0x1d21540*/
      goto LABEL_91; /*0x1d21540*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)((__int64 (__fastcall *)(struct UnityEngine_UI_Text_o *, Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *, const MethodInfo *))rankNumText->klass->vtable._75_set_text.methodPtr)( /*0x1d21558*/
                                                                          rankNumText,
                                                                          this,
                                                                          rankNumText->klass->vtable._75_set_text.method);
    eZ_6 = this_3->fields.eZ; /*0x1d2155c*/
    if ( !eZ_6 ) /*0x1d21560*/
      goto LABEL_91; /*0x1d21560*/
    stringLength = eZ_6[1].fields._stringLength; /*0x1d21564*/
    selectRankWindow = this_2->fields.selectRankWindow; /*0x1d21568*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_Int32__ToString((int)eZ_6 + 44, 0); /*0x1d21574*/
    if ( !selectRankWindow ) /*0x1d21578*/
      goto LABEL_91; /*0x1d21578*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)((__int64 (__fastcall *)(struct UnityEngine_Animator_o *, Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *, const char *))selectRankWindow->klass[4]._1.gc_desc)( /*0x1d21590*/
                                                                          selectRankWindow,
                                                                          this,
                                                                          selectRankWindow->klass[4]._1.name);
    eZ_7 = this_3->fields.eZ; /*0x1d21594*/
    if ( !eZ_7 ) /*0x1d21598*/
      goto LABEL_91; /*0x1d21598*/
    v55 = *(_QWORD *)&this_2->fields.rankNum; /*0x1d2159c*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_Int32__ToString((int)eZ_7 + 48, 0); /*0x1d215a8*/
    if ( !v55 ) /*0x1d215ac*/
      goto LABEL_91; /*0x1d215ac*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)(*(__int64 (__fastcall **)(__int64, Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *, _QWORD))(*(_QWORD *)v55 + 1512LL))( /*0x1d215c4*/
                                                                          v55,
                                                                          this,
                                                                          *(_QWORD *)(*(_QWORD *)v55 + 1520LL));
    m_CachedPtr_1 = this_3->fields.m_CachedPtr; /*0x1d215c8*/
    if ( !m_CachedPtr_1 ) /*0x1d215cc*/
      goto LABEL_91; /*0x1d215cc*/
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)this_2->fields.audioSource; /*0x1d215d0*/
    if ( !this ) /*0x1d215d4*/
      goto LABEL_91; /*0x1d215d4*/
    v21 += stringLength; /*0x1d215e0*/
    AddressableImageLoader__set_Code((AddressableImageLoader_o *)this, *(System_String_o **)(m_CachedPtr_1 + 88), 0); /*0x1d215e4*/
    Phigros2_Chapter9_ExoplanetaryMirageUnlock__ApplyIllustrationMirror( /*0x1d215f4*/
      (UnityEngine_Component_o *)this_2->fields.audioSource,
      *(System_String_o **)(v27 + 16),
      0);
    Phigros2_Chapter9_ExoplanetaryMirageUnlock__ApplyTextMirror( /*0x1d21604*/
      (UnityEngine_UI_Text_o *)this_2->fields.m_CachedPtr,
      *(System_String_o **)(v27 + 16),
      0);
    ++index; /*0x1d21608*/
  }
  while ( index != 3 ); /*0x1d21610*/
  score = this_1->fields.score; /*0x1d21618*/
  this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)System_Int32__ToString_54991860( /*0x1d2162c*/
                                                                        (int32_t)&v80 + 4,
                                                                        (System_String_o *)"D7",
                                                                        0);
  if ( !score ) /*0x1d21630*/
LABEL_91:
    sub_1B624F8(this, method); /*0x1d2189c*/
  ((void (__fastcall *)(struct UnityEngine_UI_Text_o *, Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *, const MethodInfo *))score->klass->vtable._75_set_text.methodPtr)( /*0x1d21648*/
    score,
    this,
    score->klass->vtable._75_set_text.method);
  if ( secretChallengeLifeFailed ) /*0x1d21660*/
  {
    Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl__SetRating(this_1, 0, method_1); /*0x1d2166c*/
    goto LABEL_80; /*0x1d21670*/
  }
  Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl__SetRatingWithScore( /*0x1d2169c*/
    this_1,
    SHIDWORD(v80) / 3,
    v21 == 0,
    method_2);
  Phigros2.ChallengeMode.Scripts.ChallengeModeOverControl_TypeInf = Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_TypeInfo; /*0x1d216a8*/
  if ( !Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_TypeInfo->_2.cctor_finished ) /*0x1d216ac*/
    Phigros2.ChallengeMode.Scripts.ChallengeModeOverControl_TypeInf = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_c *)j_il2cpp_runtime_class_init_0(v60, v61, v62); /*0x1d216b4*/
  Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl__UnlockC9HiddenSongKeys((const MethodInfo *)Phigros2.ChallengeMode.Scripts.ChallengeModeOverControl_TypeInf); /*0x1d216b8*/
  if ( !byte_446E372 ) /*0x1d216c0*/
  {
    sub_1B622A0(&GameInformation_TypeInfo, method_3); /*0x1d216cc*/
    byte_446E372 = 1; /*0x1d216d4*/
  }
  _main_2 = GameInformation_TypeInfo->static_fields->_main; /*0x1d216f4*/
  if ( !Phigros2_ChallengeMode_Scripts_SecretChallengeLifeRules_TypeInfo->_2.cctor_finished ) /*0x1d216f8*/
    j_il2cpp_runtime_class_init_0(v65, v66, v67); /*0x1d21700*/
  Phigros2_ChallengeMode_Scripts_SecretChallengeLifeRules__ApplyAfterSuccess(_main_2, method_3); /*0x1d21708*/
  if ( SHIDWORD(v80) >= 3000000 ) /*0x1d2171c*/
  {
    n5 = 5; /*0x1d21768*/
  }
  else if ( SHIDWORD(v80) >= 2940000 ) /*0x1d2172c*/
  {
    n5 = 4; /*0x1d21770*/
  }
  else if ( SHIDWORD(v80) >= 2850000 ) /*0x1d2173c*/
  {
    n5 = 3; /*0x1d21778*/
  }
  else if ( SHIDWORD(v80) >= 2700000 ) /*0x1d2174c*/
  {
    n5 = 2; /*0x1d21780*/
  }
  else
  {
    if ( SHIDWORD(v80) < 2460000 ) /*0x1d2175c*/
      goto LABEL_80; /*0x1d2175c*/
    n5 = 1; /*0x1d21760*/
  }
  this_1->fields.rankLevel = n5; /*0x1d21784*/
LABEL_80:
  background = this_1->fields.background; /*0x1d21788*/
  defaultValue = IdBuilder__BuildBlurBackgroundImageId((System_String_o *)"MainStoryLegacy", 0); /*0x1d217a0*/
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d217a8*/
    j_il2cpp_runtime_class_init_0(v72, v73, v74); /*0x1d217b4*/
  this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)SaveManagement__LoadString( /*0x1d217c4*/
                                                                        (System_String_o *)"LastChapterBackgroundCode",
                                                                        defaultValue,
                                                                        0);
  if ( !background ) /*0x1d217c8*/
    goto LABEL_91; /*0x1d217c8*/
  AddressableImageLoader__set_Code(background, (System_String_o *)this, 0); /*0x1d217d8*/
  background2 = this_1->fields.background2; /*0x1d217e0*/
  defaultValue_1 = IdBuilder__BuildBlurBackgroundImageId((System_String_o *)"MainStoryLegacy", 0); /*0x1d217e8*/
  this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)SaveManagement__LoadString( /*0x1d217fc*/
                                                                        (System_String_o *)"LastChapterBackgroundCode",
                                                                        defaultValue_1,
                                                                        0);
  if ( !background2 ) /*0x1d21800*/
    goto LABEL_91; /*0x1d21800*/
  AddressableImageLoader__set_Code(background2, (System_String_o *)this, 0); /*0x1d21810*/
  if ( !byte_446E372 ) /*0x1d21818*/
  {
    this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d21824*/
    byte_446E372 = 1; /*0x1d2182c*/
  }
  _main_3 = GameInformation_TypeInfo->static_fields->_main; /*0x1d21840*/
  v81.fields.gameInformation = _main_3; /*0x1d21844*/
  if ( !_main_3 ) /*0x1d21848*/
    goto LABEL_91; /*0x1d21848*/
  this = (Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_o *)this_1->fields.retryButton; /*0x1d2184c*/
  if ( !this ) /*0x1d21850*/
    goto LABEL_91; /*0x1d21850*/
  UnityEngine_UI_Selectable__set_interactable( /*0x1d21864*/
    (UnityEngine_UI_Selectable_o *)this,
    !_main_3->fields.secretChallengeLifeEnabled,
    0);
  if ( _main_3->fields.secretChallengeLifeEnabled ) /*0x1d21868*/
    Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl___Start_g__SetSecretChallengeResult_27_0( /*0x1d21878*/
      this_1,
      &v81,
      method_4);
}
