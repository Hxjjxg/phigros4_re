void Phigros2_SongSelector_Scripts_ChallengeSongSelectorControl__Play(
        Phigros2_SongSelector_Scripts_ChallengeSongSelectorControl_o *this,
        const MethodInfo *method)
{
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  __int64 challengeSongItems; // x0
  struct System_Collections_Generic_List_ChallengeSongItem__o *challengeSongItems_1; // x8
  float v11; // s0
  float v12; // s1
  float v13; // s2
  UnityEngine_Object_o *challengeGroupSource; // x20
  float v15; // s0
  float v16; // s1
  float v17; // s2
  struct SEPlayer_play_o *Play; // x8
  Il2CppObject *cutOut; // x20
  struct GameInformation_o *main; // x20
  struct System_Collections_Generic_List_ChallengeSongItem__o *challengeSongItems_2; // x8
  const MethodInfo *method_1; // x2
  bool IsNullOrEmpty; // w21
  float v24; // s0
  float v25; // s1
  float v26; // s2
  const MethodInfo *method_2; // x2
  System_Collections_IEnumerator_o *Scene; // x1

  if ( (byte_446E4D9 & 1) == 0 ) /*0x1ce1558*/
  {
    sub_1B622A0(&Method_System_Collections_Generic_List_ChallengeSongItem__RemoveAt__, method); /*0x1ce1564*/
    sub_1B622A0(&Method_System_Collections_Generic_List_ChallengeSongItem__get_Count__, v3); /*0x1ce1570*/
    sub_1B622A0(&Method_UnityEngine_Object_Instantiate_GameObject___, v4); /*0x1ce157c*/
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v5); /*0x1ce1588*/
    sub_1B622A0(&SEPlayer_TypeInfo, v6); /*0x1ce1594*/
    sub_1B622A0(&Phigros2_ChallengeMode_Scripts_SecretChallengeLifeRules_TypeInfo, v7); /*0x1ce15a0*/
    sub_1B622A0(&"ChallengeMode", v8); /*0x1ce15ac*/
    byte_446E4D9 = 1; /*0x1ce15b4*/
  }
  challengeSongItems = (__int64)this->fields.challengeSongItems; /*0x1ce15b8*/
  if ( !challengeSongItems ) /*0x1ce15bc*/
    goto LABEL_29; /*0x1ce15bc*/
  if ( *(int *)(challengeSongItems + 24) < 2 ) /*0x1ce15c8*/
    return; /*0x1ce15c8*/
  challengeSongItems = Phigros2_Chapter9_ExoplanetaryMirageUnlock__ShouldBlockChallengeStart( /*0x1ce15d0*/
                         (System_Collections_Generic_IReadOnlyList_ChallengeSongItem__o *)challengeSongItems,
                         0);
  if ( (challengeSongItems & 1) != 0 ) /*0x1ce15d4*/
    return; /*0x1ce15d4*/
  challengeSongItems_1 = this->fields.challengeSongItems; /*0x1ce15d8*/
  if ( !challengeSongItems_1 ) /*0x1ce15dc*/
    goto LABEL_29; /*0x1ce15dc*/
  if ( challengeSongItems_1->fields._size <= 2 /*0x1ce15f4*/
    && System_String__IsNullOrEmpty(this->fields._PendingSecretChallengeSongId_k__BackingField, 0) )
  {
    return; /*0x1ce15f8*/
  }
  challengeSongItems = System_String__IsNullOrEmpty(this->fields._SecretChallengeLifeTargetSongId_k__BackingField, 0); /*0x1ce1610*/
  if ( (challengeSongItems & 1) != 0 ) /*0x1ce1614*/
    goto LABEL_15; /*0x1ce1614*/
  challengeGroupSource = (UnityEngine_Object_o *)this->fields.challengeGroupSource; /*0x1ce1620*/
  if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1ce1628*/
    j_il2cpp_runtime_class_init_0(v11, v12, v13); /*0x1ce1630*/
  challengeSongItems = UnityEngine_Object__op_Equality(challengeGroupSource, 0, 0); /*0x1ce1640*/
  if ( (challengeSongItems & 1) != 0 || !this->fields.challengeGroupPlayClip ) /*0x1ce1648*/
  {
LABEL_15:
    Play = SEPlayer_TypeInfo->static_fields->Play; /*0x1ce166c*/
    if ( !Play ) /*0x1ce1670*/
      goto LABEL_29; /*0x1ce1670*/
    ((void (__fastcall *)(intptr_t, __int64, intptr_t))Play->fields.invoke_impl)( /*0x1ce1684*/
      Play->fields.method_code,
      6,
      Play->fields.method);
  }
  else
  {
    Phigros2_SongSelector_Scripts_ChallengeSongSelectorControl__PlayChallengeGroupStart(this, method); /*0x1ce1654*/
  }
  cutOut = (Il2CppObject *)this->fields.cutOut; /*0x1ce1690*/
  if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1ce1698*/
    j_il2cpp_runtime_class_init_0(v15, v16, v17); /*0x1ce16a0*/
  challengeSongItems = (__int64)UnityEngine_Object__Instantiate_object__33364316( /*0x1ce16b4*/
                                  cutOut,
                                  (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
  if ( !byte_446E372 ) /*0x1ce16c0*/
  {
    challengeSongItems = sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1ce16cc*/
    byte_446E372 = 1; /*0x1ce16d4*/
  }
  main = GameInformation_TypeInfo->static_fields->_main; /*0x1ce16e8*/
  if ( !main ) /*0x1ce16ec*/
    goto LABEL_29; /*0x1ce16ec*/
  main->fields.nowChallengeIndex = 0; /*0x1ce16f0*/
  challengeSongItems_2 = this->fields.challengeSongItems; /*0x1ce16f4*/
  main->fields.challengeSongItems = challengeSongItems_2; /*0x1ce16f8*/
  main->fields.hiddenChallengeSongItem = 0; /*0x1ce16f8*/
  if ( !challengeSongItems_2 ) /*0x1ce16fc*/
    goto LABEL_29; /*0x1ce16fc*/
  if ( challengeSongItems_2->fields._size <= 2 /*0x1ce1718*/
    && !System_String__IsNullOrEmpty(this->fields._PendingSecretChallengeSongId_k__BackingField, 0) )
  {
    Phigros2_SongSelector_Scripts_ChallengeSongSelectorControl__AppendHiddenThirdSong( /*0x1ce1724*/
      this,
      this->fields._PendingSecretChallengeSongId_k__BackingField,
      method_1);
  }
  challengeSongItems = (__int64)main->fields.challengeSongItems; /*0x1ce1728*/
  if ( !challengeSongItems ) /*0x1ce172c*/
LABEL_29:
    sub_1B624F8(challengeSongItems, method); /*0x1ce1758*/
  while ( *(int *)(challengeSongItems + 24) > 3 ) /*0x1ce1740*/
  {
    System_Collections_Generic_List_object___RemoveAt( /*0x1ce174c*/
      (System_Collections_Generic_List_object__o *)challengeSongItems,
      3,
      (const MethodInfo_2E50D2C *)Method_System_Collections_Generic_List_ChallengeSongItem__RemoveAt__);
    challengeSongItems = (__int64)main->fields.challengeSongItems; /*0x1ce1750*/
    if ( !challengeSongItems ) /*0x1ce1754*/
      goto LABEL_29; /*0x1ce1754*/
  }
  IsNullOrEmpty = System_String__IsNullOrEmpty(this->fields._SecretChallengeLifeTargetSongId_k__BackingField, 0); /*0x1ce1770*/
  if ( !Phigros2_ChallengeMode_Scripts_SecretChallengeLifeRules_TypeInfo->_2.cctor_finished ) /*0x1ce1778*/
    j_il2cpp_runtime_class_init_0(v24, v25, v26); /*0x1ce1784*/
  Phigros2_ChallengeMode_Scripts_SecretChallengeLifeRules__BeginRun(main, !IsNullOrEmpty, 0); /*0x1ce1798*/
  Scene = Phigros2_SongSelector_Scripts_ChallengeSongSelectorControl__TurnToNextScene( /*0x1ce17b0*/
            this,
            (System_String_o *)"ChallengeMode",
            method_2);
  UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)this, Scene, 0); /*0x1ce17c4*/
}
