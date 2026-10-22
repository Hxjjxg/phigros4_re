void LuminescenceLevelEffect__OnEnable(LuminescenceLevelEffect_o *this, const MethodInfo *method)
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
  UnityEngine_ShaderVariantCollection_o *lumiShaderVariantCollection; // x0
  struct GameInformation_o *main; // x8
  struct UnityEngine_Camera_o *main_1; // x0
  float v54; // s0
  float v55; // s1
  float v56; // s2
  UnityEngine_Object_o *main_2; // x20
  struct GlitchSettings_o *glitchSettings; // x8
  struct Exposure_Saturate_ContrastSettings_o *escSettings; // x9
  struct GlitchSettings_o *glitchSettings_1; // x9
  struct ScoreControl_o *m_CachedPtr; // x26
  System_Delegate_o *OnBad_k__BackingField; // x20
  System_Action_o *b; // x21
  struct ScoreControl_o *scoreControl; // x26
  System_Delegate_o *OnMiss_k__BackingField; // x20
  System_Action_o *b_1; // x21
  float v72; // s0
  float v73; // s1
  float v74; // s2
  Il2CppObject *particles; // x20
  UnityEngine_ParticleSystem_o *shootUpParticleSystem; // x8
  struct GameInformation_o *_main; // x8
  struct LevelStartInfo_o *levelStartInfo; // x8
  System_String_o *songsLevel; // x20
  float v80; // s2
  float *p_ezTargetScore; // x8
  float _targetScore; // s0
  float v83; // s1
  Il2CppObject *message; // x20
  UnityEngine_ShaderVariantCollection_o *lumiShaderVariantCollection_1; // x20
  System_Delegate_o *klass; // x21
  System_Action_o *b_2; // x22
  struct Ch8Effect_MessageController_o *messageController; // x22
  __int64 message_1; // x20
  Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *v90; // x21
  __int64 "아마 당신은 이미 이걸 알아챘을 겁니다."; // x8
  __int64 v92; // x2
  Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *v93; // x21
  __int64 "비행은 미래를 가장한 환상이고, 마차는 우리의 운명의 길을 이끌고 있습니다."; // x8
  Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *v95; // x21
  __int64 "동전은 던져진 순간부터 끝없이 뒤집혀왔고,"; // x8
  Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *v97; // x21
  __int64 "앞으로 남은 건 파괴와 환생입니다."; // x8
  struct ProgressControl_o *progressControl; // x8
  struct LevelInformation_o *levelInformation; // x8
  LuminescenceLevelEffect_o *v101; // x0
  const MethodInfo *method_1; // x1

  if ( (byte_446E813 & 1) == 0 )
  {
    sub_1B622A0(&System_Action_TypeInfo, method);
    sub_1B622A0(&Method_UnityEngine_Component_GetComponentInParent_ProgressControl___, v3);
    sub_1B622A0(&Method_UnityEngine_GameObject_AddComponent_PostProcessingManager___, v4);
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponentInChildren_Image___, v5);
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_BlackMaskControl___, v6);
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_Canvas___, v7);
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_Image___, v8);
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_MessageController___, v9);
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_ParticleSystem___, v10);
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_Text___, v11);
    sub_1B622A0(&Method_LuminescenceLevelEffect_MessageCount__, v12);
    sub_1B622A0(&Method_LuminescenceLevelEffect__OnEnable_b__48_0__, v13);
    sub_1B622A0(&Method_LuminescenceLevelEffect__OnEnable_b__48_1__, v14);
    sub_1B622A0(&Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo, v15);
    sub_1B622A0(&Method_UnityEngine_Object_Instantiate_GameObject___, v16);
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v17);
    sub_1B622A0(&string___TypeInfo, v18);
    sub_1B622A0(&"旅船は未来を覆い隠す虚像、戦車は我々の運命を導いている。", v19);
    sub_1B622A0(&"HD", v20);
    sub_1B622A0(&"Perhaps you have already caught on to this.", v21);
    sub_1B622A0(&"Combo", v22);
    sub_1B622A0(&"Score", v23);
    sub_1B622A0(&"コインが投げ出されてから、表と裏は入れ替わり続けてきた。", v24);
    sub_1B622A0(&"旅船是掩蓋未來的虛像，戰車正指引著我們的命運。", v25);
    sub_1B622A0(&"你也許也意識到了這件事。", v26);
    sub_1B622A0(&"前方是毁灭亦是重生。", v27);
    sub_1B622A0(&"Explode", v28);
    sub_1B622A0(&"前方是毀滅亦是重生。", v29);
    sub_1B622A0(&"IN", v30);
    sub_1B622A0(&"向かう先は滅びであり、新たな生でもある。", v31);
    sub_1B622A0(&"ShootUp", v32);
    sub_1B622A0(&"旅船是掩盖未来的虚像，战车正指引着我们的命运。", v33);
    sub_1B622A0(&"The flight is a phantom concealing the future, and the Chariot is leading the way to our fate.", v34);
    sub_1B622A0(&"ComboText", v35);
    sub_1B622A0(&"EZ", v36);
    sub_1B622A0(&"The coin has been flipping endlessly from the moment it was tossed,", v37);
    sub_1B622A0(&"Pause", v38);
    sub_1B622A0(&"비행은 미래를 가장한 환상이고, 마차는 우리의 운명의 길을 이끌고 있습니다.", v39);
    sub_1B622A0(&"あなたも気付いているかもしれない。", v40);
    sub_1B622A0(&"앞으로 남은 건 파괴와 환생입니다.", v41);
    sub_1B622A0(&"SongsLevel", v42);
    sub_1B622A0(&"硬幣擲出之後，正逆的轉變便從未停止。", v43);
    sub_1B622A0(&"你也许也意识到了这件事。", v44);
    sub_1B622A0(&"아마 당신은 이미 이걸 알아챘을 겁니다.", v45);
    sub_1B622A0(&"동전은 던져진 순간부터 끝없이 뒤집혀왔고,", v46);
    sub_1B622A0(&"硬币掷出之后，正逆的转换从未停止。", v47);
    sub_1B622A0(&"and what awaits ahead is destruction and rebirth.", v48);
    sub_1B622A0(&"Progress Bar", v49);
    sub_1B622A0(&"SongsName", v50);
    byte_446E813 = 1;
  }
  lumiShaderVariantCollection = this->fields.lumiShaderVariantCollection;
  if ( !lumiShaderVariantCollection )
    goto LABEL_82;
  UnityEngine_ShaderVariantCollection__WarmUp(lumiShaderVariantCollection, 0);
  if ( !byte_446E372 )
  {
    lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)sub_1B622A0(
                                                                             &GameInformation_TypeInfo,
                                                                             method);
    byte_446E372 = 1;
  }
  main = GameInformation_TypeInfo->static_fields->_main;
  if ( !main )
    goto LABEL_82;
  this->fields.origBGAlpha = main->fields.backgroundAlpha;
  if ( main->fields.backgroundAlpha > 0.8 )
    main->fields.backgroundAlpha = 0.8;
  this->fields._effectStopTime = (float)(this->fields.eventStartTime + this->fields.eventEndTime) * 0.5;
  main_1 = UnityEngine_Camera__get_main(0);
  this->fields._mainCamera = main_1;
  main_2 = (UnityEngine_Object_o *)main_1;
  if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished )
    j_il2cpp_runtime_class_init_0(v54, v55, v56);
  if ( UnityEngine_Object__op_Inequality(main_2, 0, 0) )
  {
    lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)this->fields._mainCamera;
    if ( !lumiShaderVariantCollection )
      goto LABEL_82;
    lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_Component__get_gameObject(
                                                                             (UnityEngine_Component_o *)lumiShaderVariantCollection,
                                                                             0);
    if ( !lumiShaderVariantCollection )
      goto LABEL_82;
    lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__AddComponent_object_(
                                                                             (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                             (const MethodInfo_1F8E674 *)Method_UnityEngine_GameObject_AddComponent_PostProcessingManager___);
    method = (const MethodInfo *)this->fields.escSettings;
    this->fields._postProcessingManager = (struct PostProcessingManager_o *)lumiShaderVariantCollection;
    if ( !method )
      goto LABEL_82;
    __asm { FMOV            V0.2S, #1.0 }
    method->parameters = _D0;
    LODWORD(method->rgctx_data) = 1065353216;
    glitchSettings = this->fields.glitchSettings;
    if ( !glitchSettings )
      goto LABEL_82;
    glitchSettings->fields.glitchRange = 0.0;
    if ( !lumiShaderVariantCollection )
      goto LABEL_82;
    PostProcessingManager__AddEffects(
      (PostProcessingManager_o *)lumiShaderVariantCollection,
      (PostEffectSettings_o *)method,
      0);
    lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)this->fields._postProcessingManager;
    if ( !lumiShaderVariantCollection )
      goto LABEL_82;
    PostProcessingManager__AddEffects(
      (PostProcessingManager_o *)lumiShaderVariantCollection,
      (PostEffectSettings_o *)this->fields.glitchSettings,
      0);
    escSettings = this->fields.escSettings;
    if ( !escSettings )
      goto LABEL_82;
    escSettings->fields.enabled = 1;
    glitchSettings_1 = this->fields.glitchSettings;
    if ( !glitchSettings_1 )
      goto LABEL_82;
    glitchSettings_1->fields.enabled = 1;
  }
  lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_Component__get_transform(
                                                                           (UnityEngine_Component_o *)this,
                                                                           0);
  if ( !lumiShaderVariantCollection )
    goto LABEL_82;
  lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_Component__GetComponentInParent_object_(
                                                                           (UnityEngine_Component_o *)lumiShaderVariantCollection,
                                                                           (const MethodInfo_1F472F4 *)Method_UnityEngine_Component_GetComponentInParent_ProgressControl___);
  this->fields._progressControl = (struct ProgressControl_o *)lumiShaderVariantCollection;
  if ( !lumiShaderVariantCollection )
    goto LABEL_82;
  m_CachedPtr = (struct ScoreControl_o *)lumiShaderVariantCollection[1].fields.m_CachedPtr;
  this->fields._scoreControl = m_CachedPtr;
  if ( !m_CachedPtr )
    goto LABEL_82;
  OnBad_k__BackingField = (System_Delegate_o *)m_CachedPtr->fields._OnBad_k__BackingField;
  b = (System_Action_o *)sub_1B624E8(System_Action_TypeInfo);
  System_Action___ctor(b, (Il2CppObject *)this, Method_LuminescenceLevelEffect__OnEnable_b__48_0__, 0);
  lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)System_Delegate__Combine(
                                                                           OnBad_k__BackingField,
                                                                           (System_Delegate_o *)b,
                                                                           0);
  method = (const MethodInfo *)System_Action_TypeInfo;
  if ( !lumiShaderVariantCollection || (System_Action_c *)lumiShaderVariantCollection->klass == System_Action_TypeInfo )
  {
    m_CachedPtr->fields._OnBad_k__BackingField = (struct System_Action_o *)lumiShaderVariantCollection;
    scoreControl = this->fields._scoreControl;
    if ( !scoreControl )
      goto LABEL_82;
    OnMiss_k__BackingField = (System_Delegate_o *)scoreControl->fields._OnMiss_k__BackingField;
    b_1 = (System_Action_o *)sub_1B624E8(method);
    System_Action___ctor(b_1, (Il2CppObject *)this, Method_LuminescenceLevelEffect__OnEnable_b__48_1__, 0);
    lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)System_Delegate__Combine(
                                                                             OnMiss_k__BackingField,
                                                                             (System_Delegate_o *)b_1,
                                                                             0);
    if ( !lumiShaderVariantCollection || (System_Action_c *)lumiShaderVariantCollection->klass == System_Action_TypeInfo )
    {
      scoreControl->fields._OnMiss_k__BackingField = (struct System_Action_o *)lumiShaderVariantCollection;
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__Find(
                                                                               (System_String_o *)"Pause",
                                                                               0);
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      this->fields._pause = (struct UnityEngine_UI_Image_o *)UnityEngine_GameObject__GetComponent_object_(
                                                               (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                               (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Image___);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__Find(
                                                                               (System_String_o *)"Progress Bar",
                                                                               0);
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      this->fields._progressBar = (struct UnityEngine_UI_Image_o *)UnityEngine_GameObject__GetComponent_object_(
                                                                     (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                     (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Image___);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__Find(
                                                                               (System_String_o *)"Score",
                                                                               0);
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      this->fields._score = (struct UnityEngine_UI_Text_o *)UnityEngine_GameObject__GetComponent_object_(
                                                              (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                              (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Text___);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__Find(
                                                                               (System_String_o *)"SongsLevel",
                                                                               0);
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      this->fields._songsLevel = (struct UnityEngine_UI_Text_o *)UnityEngine_GameObject__GetComponent_object_(
                                                                   (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                   (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Text___);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__Find(
                                                                               (System_String_o *)"SongsName",
                                                                               0);
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      this->fields._songsName = (struct UnityEngine_UI_Text_o *)UnityEngine_GameObject__GetComponent_object_(
                                                                  (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                  (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Text___);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__Find(
                                                                               (System_String_o *)"Combo",
                                                                               0);
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      this->fields._combo = (struct UnityEngine_UI_Text_o *)UnityEngine_GameObject__GetComponent_object_(
                                                              (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                              (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Text___);
      this->fields._comboText = UnityEngine_GameObject__Find((System_String_o *)"ComboText", 0);
      particles = (Il2CppObject *)this->fields.particles;
      if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v72, v73, v74);
      this->fields.particles = (struct UnityEngine_GameObject_o *)UnityEngine_Object__Instantiate_object__33364316(
                                                                    particles,
                                                                    (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__Find(
                                                                               (System_String_o *)"ShootUp",
                                                                               0);
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      this->fields._shootUpParticleSystem = (struct UnityEngine_ParticleSystem_o *)UnityEngine_GameObject__GetComponent_object_(
                                                                                     (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                                     (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_ParticleSystem___);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__Find(
                                                                               (System_String_o *)"Explode",
                                                                               0);
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__GetComponent_object_(
                                                                               (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                               (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_ParticleSystem___);
      shootUpParticleSystem = this->fields._shootUpParticleSystem;
      this->fields._explodeParticleSystem = (struct UnityEngine_ParticleSystem_o *)lumiShaderVariantCollection;
      if ( !shootUpParticleSystem )
        goto LABEL_82;
      UnityEngine_ParticleSystem__Stop_63633084(shootUpParticleSystem, 0);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)this->fields._explodeParticleSystem;
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      UnityEngine_ParticleSystem__Stop_63633084((UnityEngine_ParticleSystem_o *)lumiShaderVariantCollection, 0);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)this->fields.particles;
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__GetComponent_object_(
                                                                               (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                               (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Canvas___);
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      UnityEngine_Canvas__set_worldCamera(
        (UnityEngine_Canvas_o *)lumiShaderVariantCollection,
        this->fields._mainCamera,
        0);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_Object__Instantiate_object__33364316(
                                                                               (Il2CppObject *)this->fields.blackBg,
                                                                               (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
      this->fields.blackBg = (struct UnityEngine_GameObject_o *)lumiShaderVariantCollection;
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__GetComponent_object_(
                                                                               (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                               (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Canvas___);
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      UnityEngine_Canvas__set_worldCamera(
        (UnityEngine_Canvas_o *)lumiShaderVariantCollection,
        this->fields._mainCamera,
        0);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)this->fields.blackBg;
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      this->fields._blackBgImage = (struct UnityEngine_UI_Image_o *)UnityEngine_GameObject__GetComponentInChildren_object_(
                                                                      (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                      (const MethodInfo_1F8EABC *)Method_UnityEngine_GameObject_GetComponentInChildren_Image___);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_Object__Instantiate_object__33364316(
                                                                               (Il2CppObject *)this->fields.blackMask,
                                                                               (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
      this->fields.blackMask = (struct UnityEngine_GameObject_o *)lumiShaderVariantCollection;
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__GetComponent_object_(
                                                                               (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                               (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Canvas___);
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      UnityEngine_Canvas__set_worldCamera(
        (UnityEngine_Canvas_o *)lumiShaderVariantCollection,
        this->fields._mainCamera,
        0);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)this->fields.blackMask;
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__GetComponent_object_(
                                                                               (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                               (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_BlackMaskControl___);
      this->fields._blackMaskControl = (struct BlackMaskControl_o *)lumiShaderVariantCollection;
      if ( !byte_446E372 )
      {
        lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)sub_1B622A0(
                                                                                 &GameInformation_TypeInfo,
                                                                                 method);
        byte_446E372 = 1;
      }
      _main = GameInformation_TypeInfo->static_fields->_main;
      if ( !_main )
        goto LABEL_82;
      levelStartInfo = _main->fields.levelStartInfo;
      if ( !levelStartInfo )
        goto LABEL_82;
      songsLevel = levelStartInfo->fields.songsLevel;
      if ( System_String__op_Equality(songsLevel, (System_String_o *)"EZ", 0) )
      {
        p_ezTargetScore = &this->fields.ezTargetScore;
      }
      else if ( System_String__op_Equality(songsLevel, (System_String_o *)"HD", 0) )
      {
        p_ezTargetScore = &this->fields.hdTargetScore;
      }
      else
      {
        if ( !System_String__op_Equality(songsLevel, (System_String_o *)"IN", 0) )
          goto LABEL_60;
        p_ezTargetScore = &this->fields.inTargetScore;
      }
      v83 = 0.92;
      _targetScore = *p_ezTargetScore * 0.92;
      this->fields._targetScore = _targetScore;
LABEL_60:
      message = (Il2CppObject *)this->fields.message;
      if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(_targetScore, v83, v80);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_Object__Instantiate_object__33364316(
                                                                               message,
                                                                               (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
      this->fields.message = (struct UnityEngine_GameObject_o *)lumiShaderVariantCollection;
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__GetComponent_object_(
                                                                               (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                               (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Canvas___);
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      UnityEngine_Canvas__set_worldCamera(
        (UnityEngine_Canvas_o *)lumiShaderVariantCollection,
        this->fields._mainCamera,
        0);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)this->fields.message;
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)UnityEngine_GameObject__GetComponent_object_(
                                                                               (UnityEngine_GameObject_o *)lumiShaderVariantCollection,
                                                                               (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_MessageController___);
      this->fields._messageController = (struct Ch8Effect_MessageController_o *)lumiShaderVariantCollection;
      if ( !lumiShaderVariantCollection )
        goto LABEL_82;
      lumiShaderVariantCollection_1 = lumiShaderVariantCollection;
      klass = (System_Delegate_o *)lumiShaderVariantCollection[4].klass;
      b_2 = (System_Action_o *)sub_1B624E8(System_Action_TypeInfo);
      System_Action___ctor(b_2, (Il2CppObject *)this, Method_LuminescenceLevelEffect_MessageCount__, 0);
      lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)System_Delegate__Combine(
                                                                               klass,
                                                                               (System_Delegate_o *)b_2,
                                                                               0);
      if ( !lumiShaderVariantCollection
        || (System_Action_c *)lumiShaderVariantCollection->klass == System_Action_TypeInfo )
      {
        lumiShaderVariantCollection_1[4].klass = (UnityEngine_ShaderVariantCollection_c *)lumiShaderVariantCollection;
        messageController = this->fields._messageController;
        message_1 = sub_1B62344(string___TypeInfo, 4);
        v90 = (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)sub_1B624E8(Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo);
        Phigros2_Utils_MultiLanguageText_MultiLanguageTextString___ctor(v90, 0);
        if ( v90 )
        {
          v90->fields.chinese = (struct System_String_o *)"你也许也意识到了这件事。";
          v90->fields.chineseTraditional = (struct System_String_o *)"你也許也意識到了這件事。";
          v90->fields.english = (struct System_String_o *)"Perhaps you have already caught on to this.";
          "아마 당신은 이미 이걸 알아챘을 겁니다." = "아마 당신은 이미 이걸 알아챘을 겁니다.";
          v90->fields.japanese = (struct System_String_o *)"あなたも気付いているかもしれない。";
          v90->fields.korean = (struct System_String_o *)"아마 당신은 이미 이걸 알아챘을 겁니다.";
          lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)Phigros2_Utils_MultiLanguageText_MultiLanguageTextString__get_LocaleText(
                                                                                   v90,
                                                                                   0);
          if ( message_1 )
          {
            if ( !*(_DWORD *)(message_1 + 24) )
              goto LABEL_83;
            *(_QWORD *)(message_1 + 32) = lumiShaderVariantCollection;
            v93 = (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)sub_1B624E8(Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo);
            Phigros2_Utils_MultiLanguageText_MultiLanguageTextString___ctor(v93, 0);
            if ( !v93 )
              goto LABEL_82;
            v93->fields.chinese = (struct System_String_o *)"旅船是掩盖未来的虚像，战车正指引着我们的命运。";
            v93->fields.chineseTraditional = (struct System_String_o *)"旅船是掩蓋未來的虛像，戰車正指引著我們的命運。";
            v93->fields.english = (struct System_String_o *)"The flight is a phantom concealing the future, and the Chariot is leading the way to our fate.";
            "비행은 미래를 가장한 환상이고, 마차는 우리의 운명의 길을 이끌고 있습니다." = "비행은 미래를 가장한 환상이고, 마차는 우리의 운명의 길을 이끌고 있습니다.";
            v93->fields.japanese = (struct System_String_o *)"旅船は未来を覆い隠す虚像、戦車は我々の運命を導いている。";
            v93->fields.korean = (struct System_String_o *)"비행은 미래를 가장한 환상이고, 마차는 우리의 운명의 길을 이끌고 있습니다.";
            lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)Phigros2_Utils_MultiLanguageText_MultiLanguageTextString__get_LocaleText(
                                                                                     v93,
                                                                                     0);
            if ( *(_DWORD *)(message_1 + 24) <= 1u )
              goto LABEL_83;
            *(_QWORD *)(message_1 + 40) = lumiShaderVariantCollection;
            v95 = (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)sub_1B624E8(Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo);
            Phigros2_Utils_MultiLanguageText_MultiLanguageTextString___ctor(v95, 0);
            if ( !v95 )
              goto LABEL_82;
            v95->fields.chinese = (struct System_String_o *)"硬币掷出之后，正逆的转换从未停止。";
            v95->fields.chineseTraditional = (struct System_String_o *)"硬幣擲出之後，正逆的轉變便從未停止。";
            v95->fields.english = (struct System_String_o *)"The coin has been flipping endlessly from the moment it was tossed,";
            "동전은 던져진 순간부터 끝없이 뒤집혀왔고," = "동전은 던져진 순간부터 끝없이 뒤집혀왔고,";
            v95->fields.japanese = (struct System_String_o *)"コインが投げ出されてから、表と裏は入れ替わり続けてきた。";
            v95->fields.korean = (struct System_String_o *)"동전은 던져진 순간부터 끝없이 뒤집혀왔고,";
            lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)Phigros2_Utils_MultiLanguageText_MultiLanguageTextString__get_LocaleText(
                                                                                     v95,
                                                                                     0);
            if ( *(_DWORD *)(message_1 + 24) <= 2u )
              goto LABEL_83;
            *(_QWORD *)(message_1 + 48) = lumiShaderVariantCollection;
            v97 = (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)sub_1B624E8(Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo);
            Phigros2_Utils_MultiLanguageText_MultiLanguageTextString___ctor(v97, 0);
            if ( !v97 )
              goto LABEL_82;
            v97->fields.chinese = (struct System_String_o *)"前方是毁灭亦是重生。";
            v97->fields.chineseTraditional = (struct System_String_o *)"前方是毀滅亦是重生。";
            v97->fields.english = (struct System_String_o *)"and what awaits ahead is destruction and rebirth.";
            "앞으로 남은 건 파괴와 환생입니다." = "앞으로 남은 건 파괴와 환생입니다.";
            v97->fields.japanese = (struct System_String_o *)"向かう先は滅びであり、新たな生でもある。";
            v97->fields.korean = (struct System_String_o *)"앞으로 남은 건 파괴와 환생입니다.";
            lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)Phigros2_Utils_MultiLanguageText_MultiLanguageTextString__get_LocaleText(
                                                                                     v97,
                                                                                     0);
            if ( *(_DWORD *)(message_1 + 24) <= 3u )
LABEL_83:
              sub_1B62500(lumiShaderVariantCollection, method, v92);
            *(_QWORD *)(message_1 + 56) = lumiShaderVariantCollection;
            if ( messageController )
            {
              messageController->fields.message = (struct System_String_array *)message_1;
              lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)this->fields._pause;
              if ( lumiShaderVariantCollection )
              {
                lumiShaderVariantCollection = (UnityEngine_ShaderVariantCollection_o *)(*(__int64 (__fastcall **)(UnityEngine_ShaderVariantCollection_o *, Il2CppMethodPointer, float, float, float, float))&lumiShaderVariantCollection->klass[1]._2.naturalAligment)(
                                                                                         lumiShaderVariantCollection,
                                                                                         lumiShaderVariantCollection->klass[1].vtable._0_Equals.methodPtr,
                                                                                         1.0,
                                                                                         1.0,
                                                                                         1.0,
                                                                                         0.2);
                progressControl = this->fields._progressControl;
                if ( progressControl )
                {
                  levelInformation = progressControl->fields.levelInformation;
                  if ( levelInformation )
                  {
                    levelInformation->fields.canPause = 0;
                    return;
                  }
                }
              }
            }
          }
        }
LABEL_82:
        sub_1B624F8(lumiShaderVariantCollection, method);
      }
    }
  }
  v101 = (LuminescenceLevelEffect_o *)sub_1B62898(lumiShaderVariantCollection);
  LuminescenceLevelEffect__OnDisable(v101, method_1);
}
