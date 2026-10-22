void Phigros2_Chapter9_Chapter9Phase1Control__Start(
        Phigros2_Chapter9_Chapter9Phase1Control_o *this,
        const MethodInfo *method)
{
  float v2; // s0
  float v3; // s1
  float v4; // s2
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
  const MethodInfo *method_1; // x1
  float v28; // s0
  float v29; // s1
  float v30; // s2
  UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_c *UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB; // x0
  System_Collections_IEnumerator_o *routine; // x0
  float v33; // s0
  float v34; // s1
  float v35; // s2
  float v36; // s0
  float v37; // s1
  float v38; // s2
  __int64 Int; // x0
  __int64 v40; // x1
  struct GameInformation_o *main; // x8
  const MethodInfo *method_2; // x1
  __int64 v43; // x2
  struct UnityEngine_UI_Text_array *collectionButtonTexts; // x22
  int max_length; // w8
  unsigned int max_length_1; // w23
  UnityEngine_UI_Text_o *v47; // x20
  struct UnityEngine_UI_Text_array *settingsButtonTexts; // x22
  int max_length_2; // w8
  unsigned int max_length_3; // w23
  UnityEngine_UI_Text_o *v51; // x20
  const MethodInfo *method_3; // x1
  System_Collections_IEnumerator_o *routine_1; // x0
  System_Action_o *_0___Init; // x20
  float v55; // s0
  float v56; // s1
  float v57; // s2
  UnityEngine_Component_o *upButton; // x8
  char Int_1; // w19

  if ( (byte_446E600 & 1) == 0 ) /*0x1cf87f0*/
  {
    sub_1B622A0(&System_Action_TypeInfo, method); /*0x1cf87fc*/
    sub_1B622A0(&Method_AssetStore_Get_AudioClip___, v6); /*0x1cf8808*/
    sub_1B622A0(&AssetStore_TypeInfo, v7); /*0x1cf8814*/
    sub_1B622A0(&UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo, v8); /*0x1cf8820*/
    sub_1B622A0(&Method_Chapter9MapElement_Init__, v9); /*0x1cf882c*/
    sub_1B622A0(&GameInformation_TypeInfo, v10); /*0x1cf8838*/
    sub_1B622A0(&Method_System_Nullable_bool__GetValueOrDefault__, v11); /*0x1cf8844*/
    sub_1B622A0(&Method_System_Nullable_bool__get_HasValue__, v12); /*0x1cf8850*/
    sub_1B622A0(&SaveManagement_TypeInfo, v13); /*0x1cf885c*/
    sub_1B622A0(&Phigros2_Chapter9_Chapter9Phase1Control___O_TypeInfo, v14); /*0x1cf8868*/
    sub_1B622A0(&"C9S6Unlocked", v15); /*0x1cf8874*/
    sub_1B622A0(&"Chapter9UnlockBegin", v16); /*0x1cf8880*/
    sub_1B622A0(&"C9EvanescentUnlocked", v17); /*0x1cf888c*/
    sub_1B622A0(&"Chapter9LastSelectMusicIsPhase2", v18); /*0x1cf8898*/
    sub_1B622A0(&"NowLevel", v19); /*0x1cf88a4*/
    sub_1B622A0(&"MainStory9", v20); /*0x1cf88b0*/
    sub_1B622A0(&"Chapter9SelectMusic", v21); /*0x1cf88bc*/
    sub_1B622A0(&"Chapter9Phase2Passed", v22); /*0x1cf88c8*/
    sub_1B622A0(&"C9AboutTheUniverseUnlocked", v23); /*0x1cf88d4*/
    sub_1B622A0(&"C9EntranceToTheChaosUnlocked", v24); /*0x1cf88e0*/
    sub_1B622A0(&"chapter9.sound.glitch", v25); /*0x1cf88ec*/
    sub_1B622A0(&"C9ImplexroughUnlocked", v26); /*0x1cf88f8*/
    byte_446E600 = 1; /*0x1cf8900*/
  }
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cf8910*/
    j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1cf8920*/
  SaveManagement__SaveBool((System_String_o *)"Chapter9LastSelectMusicIsPhase2", 0, 0); /*0x1cf8930*/
  if ( !UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo->_2.cctor_finished ) /*0x1cf8938*/
    j_il2cpp_runtime_class_init_0(v28, v29, v30); /*0x1cf8940*/
  if ( !byte_446E6A8 ) /*0x1cf894c*/
  {
    sub_1B622A0(&UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo, method_1); /*0x1cf8958*/
    byte_446E6A8 = 1; /*0x1cf8960*/
  }
  UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB = UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo; /*0x1cf8964*/
  if ( !UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo->_2.cctor_finished ) /*0x1cf8968*/
  {
    j_il2cpp_runtime_class_init_0(v28, v29, v30); /*0x1cf8970*/
    UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB = UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo; /*0x1cf8974*/
  }
  if ( *(_WORD *)&UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB->static_fields->_PasswordIsCorrect_k__BackingField > 0xFFu /*0x1cf8998*/
    || !(unsigned __int8)*(_WORD *)&UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB->static_fields->_PasswordIsCorrect_k__BackingField )
  {
    routine = Phigros2_Chapter9_Chapter9Phase1Control__ReplaceSecretCollectionWhenReady(this, method_1); /*0x1cf89a4*/
    UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)this, routine, 0); /*0x1cf89b4*/
  }
  GameInformation_TypeInfo->static_fields->nowChapterCode = (struct System_String_o *)"MainStory9"; /*0x1cf89c8*/
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cf89d0*/
    j_il2cpp_runtime_class_init_0(v28, v29, v30); /*0x1cf89dc*/
  if ( SaveManagement__LoadBool((System_String_o *)"C9ImplexroughUnlocked", 0, 0) ) /*0x1cf89ec*/
  {
    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cf89fc*/
      j_il2cpp_runtime_class_init_0(v33, v34, v35); /*0x1cf8a08*/
    if ( SaveManagement__LoadBool((System_String_o *)"C9AboutTheUniverseUnlocked", 0, 0) ) /*0x1cf8a18*/
    {
      if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cf8a24*/
        j_il2cpp_runtime_class_init_0(v33, v34, v35); /*0x1cf8a2c*/
      if ( SaveManagement__LoadBool((System_String_o *)"C9EvanescentUnlocked", 0, 0) ) /*0x1cf8a44*/
      {
        if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cf8a50*/
          j_il2cpp_runtime_class_init_0(v33, v34, v35); /*0x1cf8a58*/
        if ( !SaveManagement__LoadBool((System_String_o *)"C9EntranceToTheChaosUnlocked", 0, 0) ) /*0x1cf8a74*/
        {
          if ( !AssetStore_TypeInfo->_2.cctor_finished ) /*0x1cf8a84*/
            j_il2cpp_runtime_class_init_0(v33, v34, v35); /*0x1cf8a8c*/
          this->fields.fakeChallengeGlitchAudioRef = (struct AssetStore_AssetRef_AudioClip__o *)AssetStore__Get_object_( /*0x1cf8aac*/
                                                                                                  (System_String_o *)"chapter9.sound.glitch",
                                                                                                  (const MethodInfo_1EE8C20 *)Method_AssetStore_Get_AudioClip___);
        }
      }
    }
  }
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cf8ab8*/
    j_il2cpp_runtime_class_init_0(v33, v34, v35); /*0x1cf8ac4*/
  if ( !SaveManagement__LoadBool((System_String_o *)"Chapter9UnlockBegin", 0, 0) ) /*0x1cf8ad8*/
  {
    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cf8ae0*/
      j_il2cpp_runtime_class_init_0(v36, v37, v38); /*0x1cf8ae8*/
    SaveManagement__SaveBool((System_String_o *)"Chapter9UnlockBegin", 1, 0); /*0x1cf8af8*/
  }
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cf8b04*/
    j_il2cpp_runtime_class_init_0(v36, v37, v38); /*0x1cf8b10*/
  Int = SaveManagement__LoadInt((System_String_o *)"NowLevel", 0, 0); /*0x1cf8b20*/
  this->fields.nowLevel = Int; /*0x1cf8b24*/
  if ( !byte_446E372 ) /*0x1cf8b30*/
  {
    Int = sub_1B622A0(&GameInformation_TypeInfo, v40); /*0x1cf8b3c*/
    byte_446E372 = 1; /*0x1cf8b44*/
  }
  main = GameInformation_TypeInfo->static_fields->_main; /*0x1cf8b50*/
  if ( !main ) /*0x1cf8b54*/
    goto LABEL_67; /*0x1cf8b54*/
  Int = (__int64)main->fields.getCollectionControl; /*0x1cf8b58*/
  if ( !Int ) /*0x1cf8b5c*/
    goto LABEL_67; /*0x1cf8b5c*/
  GetCollectionControl__HandleSceneEntered((GetCollectionControl_o *)Int, (System_String_o *)"Chapter9SelectMusic", 0); /*0x1cf8b78*/
  Phigros2_Chapter9_Chapter9Phase1Control__BuildSongList(this, method_2); /*0x1cf8b80*/
  if ( SaveManagement__LoadBool((System_String_o *)"Chapter9Phase2Passed", 0, 0) ) /*0x1cf8b90*/
  {
    Int = (__int64)this->fields.menuFull; /*0x1cf8b98*/
    if ( !Int ) /*0x1cf8b9c*/
      goto LABEL_67; /*0x1cf8b9c*/
  }
  else
  {
    Int = (__int64)this->fields.menu; /*0x1cf8ba4*/
    if ( !Int ) /*0x1cf8ba8*/
      goto LABEL_67; /*0x1cf8ba8*/
  }
  UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)Int, 1, 0); /*0x1cf8bb4*/
  collectionButtonTexts = this->fields.collectionButtonTexts; /*0x1cf8bb8*/
  if ( !collectionButtonTexts ) /*0x1cf8bbc*/
    goto LABEL_67; /*0x1cf8bbc*/
  max_length = collectionButtonTexts->max_length; /*0x1cf8bc0*/
  if ( max_length >= 1 ) /*0x1cf8bc8*/
  {
    max_length_1 = 0; /*0x1cf8bcc*/
    while ( max_length_1 < max_length ) /*0x1cf8bd4*/
    {
      Int = (__int64)this->fields.collectionText; /*0x1cf8bd8*/
      if ( !Int ) /*0x1cf8bdc*/
        goto LABEL_67; /*0x1cf8bdc*/
      v47 = collectionButtonTexts->m_Items[max_length_1]; /*0x1cf8be4*/
      Int = (__int64)Phigros2_Utils_MultiLanguageText_MultiLanguageTextString__get_LocaleText( /*0x1cf8bec*/
                       (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)Int,
                       0);
      if ( !v47 ) /*0x1cf8bf0*/
        goto LABEL_67; /*0x1cf8bf0*/
      Int = ((__int64 (__fastcall *)(UnityEngine_UI_Text_o *, __int64, const MethodInfo *))v47->klass->vtable._75_set_text.methodPtr)( /*0x1cf8c08*/
              v47,
              Int,
              v47->klass->vtable._75_set_text.method);
      max_length = collectionButtonTexts->max_length; /*0x1cf8c0c*/
      if ( (int)++max_length_1 >= max_length ) /*0x1cf8c18*/
        goto LABEL_52; /*0x1cf8c18*/
    }
LABEL_68:
    sub_1B62500(Int, v40, v43); /*0x1cf8d74*/
  }
LABEL_52:
  settingsButtonTexts = this->fields.settingsButtonTexts; /*0x1cf8c1c*/
  if ( !settingsButtonTexts ) /*0x1cf8c20*/
    goto LABEL_67; /*0x1cf8c20*/
  max_length_2 = settingsButtonTexts->max_length; /*0x1cf8c24*/
  if ( max_length_2 >= 1 ) /*0x1cf8c2c*/
  {
    max_length_3 = 0; /*0x1cf8c30*/
    while ( max_length_3 < max_length_2 ) /*0x1cf8c38*/
    {
      Int = (__int64)this->fields.settingsText; /*0x1cf8c3c*/
      if ( !Int ) /*0x1cf8c40*/
        goto LABEL_67; /*0x1cf8c40*/
      v51 = settingsButtonTexts->m_Items[max_length_3]; /*0x1cf8c48*/
      Int = (__int64)Phigros2_Utils_MultiLanguageText_MultiLanguageTextString__get_LocaleText( /*0x1cf8c50*/
                       (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)Int,
                       0);
      if ( !v51 ) /*0x1cf8c54*/
        goto LABEL_67; /*0x1cf8c54*/
      Int = ((__int64 (__fastcall *)(UnityEngine_UI_Text_o *, __int64, const MethodInfo *))v51->klass->vtable._75_set_text.methodPtr)( /*0x1cf8c6c*/
              v51,
              Int,
              v51->klass->vtable._75_set_text.method);
      max_length_2 = settingsButtonTexts->max_length; /*0x1cf8c70*/
      if ( (int)++max_length_3 >= max_length_2 ) /*0x1cf8c7c*/
        goto LABEL_59; /*0x1cf8c7c*/
    }
    goto LABEL_68; /*0x1cf8c38*/
  }
LABEL_59:
  Int = (__int64)this->fields.bgmPlayer; /*0x1cf8c80*/
  this->fields.onAnim = 1; /*0x1cf8c88*/
  if ( !Int ) /*0x1cf8c8c*/
    goto LABEL_67; /*0x1cf8c8c*/
  UnityEngine_AudioSource__set_volume((UnityEngine_AudioSource_o *)Int, 0.0, 0); /*0x1cf8ca0*/
  routine_1 = Phigros2_Chapter9_Chapter9Phase1Control__PlayUnlockReturnSequence(this, method_3); /*0x1cf8ca8*/
  UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)this, routine_1, 0); /*0x1cf8cb8*/
  _0___Init = Phigros2_Chapter9_Chapter9Phase1Control___O_TypeInfo->static_fields->_0___Init; /*0x1cf8cc4*/
  if ( !_0___Init ) /*0x1cf8cc8*/
  {
    _0___Init = (System_Action_o *)sub_1B624E8(System_Action_TypeInfo); /*0x1cf8cec*/
    System_Action___ctor(_0___Init, 0, Method_Chapter9MapElement_Init__, 0); /*0x1cf8cf4*/
    Phigros2_Chapter9_Chapter9Phase1Control___O_TypeInfo->static_fields->_0___Init = _0___Init; /*0x1cf8d00*/
  }
  SimpleCoroutine__SimpleYield((UnityEngine_MonoBehaviour_o *)this, _0___Init, 0); /*0x1cf8d18*/
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cf8d20*/
    j_il2cpp_runtime_class_init_0(v55, v56, v57); /*0x1cf8d28*/
  Int = SaveManagement__LoadBool((System_String_o *)"C9S6Unlocked", 0, 0); /*0x1cf8d38*/
  upButton = (UnityEngine_Component_o *)this->fields.upButton; /*0x1cf8d3c*/
  if ( !upButton || (Int_1 = Int, (Int = (__int64)UnityEngine_Component__get_gameObject(upButton, 0)) == 0) ) /*0x1cf8d54*/
LABEL_67:
    sub_1B624F8(Int, v40); /*0x1cf8d70*/
  UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)Int, Int_1 & 1, 0); /*0x1cf8d6c*/
}
