  *(_QWORD *)(onValueChanged_5 + 32) = v175; /*0x1d94848*/
  if ( !settingItemsManager_5 ) /*0x1d9484c*/
    goto LABEL_125; /*0x1d9484c*/
  this->fields.hitsoundVolumeSlider = SettingItemsManager__AddSlider_31033428( /*0x1d94874*/
                                        settingItemsManager_5,
                                        title_5,
                                        HitFXVol,
                                        0.00001,
                                        1.0,
                                        (UnityEngine_Events_UnityAction_float__array *)onValueChanged_5,
                                        0);
  settingItemsManager_6 = this->fields.settingItemsManager; /*0x1d9487c*/
  title_6 = (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)sub_1B624E8(Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo); /*0x1d94888*/
  Phigros2_Utils_MultiLanguageText_MultiLanguageTextString___ctor(title_6, 0); /*0x1d9488c*/
  if ( !title_6 ) /*0x1d94890*/
    goto LABEL_125; /*0x1d94890*/
  title_6->fields.chinese = (struct System_String_o *)StringLiteral_16232; /*0x1d948b4*/
  title_6->fields.chineseTraditional = (struct System_String_o *)StringLiteral_16463; /*0x1d948c4*/
  title_6->fields.english = (struct System_String_o *)StringLiteral_5016; /*0x1d948d4*/
  title_6->fields.japanese = (struct System_String_o *)StringLiteral_16200; /*0x1d948e4*/
  title_6->fields.korean = (struct System_String_o *)StringLiteral_16541; /*0x1d948f4*/
  Bool = SaveManagement__LoadBool((System_String_o *)StringLiteral_11936, 1, 0); /*0x1d94908*/
  Component_object = sub_1B62344(UnityEngine_Events_UnityAction_bool____TypeInfo, 1); /*0x1d94914*/
  Setting.__c_TypeInfo = Setting___c_TypeInfo; /*0x1d94918*/
  Component_object_1 = Component_object; /*0x1d9491c*/
  if ( !Setting___c_TypeInfo->_2.cctor_finished ) /*0x1d94920*/
  {
    Component_object = j_il2cpp_runtime_class_init_0(v179, v180, v181); /*0x1d9492c*/
    Setting.__c_TypeInfo = Setting___c_TypeInfo; /*0x1d94930*/
  }
  _9__47_6 = Setting.__c_TypeInfo->static_fields->__9__47_6; /*0x1d94938*/
  if ( !_9__47_6 ) /*0x1d9493c*/
  {
    if ( !Setting.__c_TypeInfo->_2.cctor_finished ) /*0x1d94940*/
    {
      j_il2cpp_runtime_class_init_0(v179, v180, v181); /*0x1d9494c*/
      Setting.__c_TypeInfo = Setting___c_TypeInfo; /*0x1d94950*/
    }
    _ = (Il2CppObject *)Setting.__c_TypeInfo->static_fields->__9; /*0x1d9495c*/
    _9__47_6 = (UnityEngine_Events_UnityAction_bool__o *)sub_1B624E8(UnityEngine_Events_UnityAction_bool__TypeInfo); /*0x1d94974*/
    UnityEngine_Events_UnityAction_bool____ctor(_9__47_6, _, Method_Setting___c__Start_b__47_6__, 0); /*0x1d9497c*/
    Setting___c_TypeInfo->static_fields->__9__47_6 = _9__47_6; /*0x1d94988*/
  }
  if ( !Component_object_1 ) /*0x1d9498c*/
    goto LABEL_125; /*0x1d9498c*/
  if ( !*(_DWORD *)(Component_object_1 + 24) ) /*0x1d94994*/
    goto LABEL_126; /*0x1d94994*/
  *(_QWORD *)(Component_object_1 + 32) = _9__47_6; /*0x1d949a0*/
  if ( !settingItemsManager_6 ) /*0x1d949a4*/
    goto LABEL_125; /*0x1d949a4*/
  SettingItemsManager__AddToggle_31033964( /*0x1d949c4*/
    settingItemsManager_6,
    title_6,
    Bool,
    (UnityEngine_Events_UnityAction_bool__array *)Component_object_1,
    0);
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d949cc*/
    j_il2cpp_runtime_class_init_0(v186, v187, v188); /*0x1d949d4*/
  value_8 = SaveManagement__LoadBool((System_String_o *)StringLiteral_11331, 0, 0); /*0x1d949e4*/
  settingItemsManager_7 = this->fields.settingItemsManager; /*0x1d949ec*/
  value_2 = value_8; /*0x1d949f0*/
  title_7 = (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)sub_1B624E8(Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo); /*0x1d94a00*/
  Phigros2_Utils_MultiLanguageText_MultiLanguageTextString___ctor(title_7, 0); /*0x1d94a04*/
  if ( !title_7 ) /*0x1d94a08*/
    goto LABEL_125; /*0x1d94a08*/
  title_7->fields.chinese = (struct System_String_o *)StringLiteral_16230; /*0x1d94a28*/
  title_7->fields.chineseTraditional = (struct System_String_o *)StringLiteral_16461; /*0x1d94a38*/
  title_7->fields.english = (struct System_String_o *)StringLiteral_4491; /*0x1d94a48*/
  StringLiteral_4492 = StringLiteral_4492; /*0x1d94a58*/
  title_7->fields.japanese = (struct System_String_o *)StringLiteral_4493; /*0x1d94a5c*/
  title_7->fields.korean = (struct System_String_o *)StringLiteral_4492; /*0x1d94a5c*/
  onValueChanged_6 = sub_1B62344(UnityEngine_Events_UnityAction_bool____TypeInfo, 1); /*0x1d94a70*/
  v195 = (UnityEngine_Events_UnityAction_bool__o *)sub_1B624E8(UnityEngine_Events_UnityAction_bool__TypeInfo); /*0x1d94a88*/
  UnityEngine_Events_UnityAction_bool____ctor(v195, (Il2CppObject *)this, Method_Setting_ChangeFCAP__, 0); /*0x1d94a8c*/
  if ( !onValueChanged_6 ) /*0x1d94a90*/
    goto LABEL_125; /*0x1d94a90*/
  if ( !*(_DWORD *)(onValueChanged_6 + 24) ) /*0x1d94a94*/
    goto LABEL_126; /*0x1d94a94*/
  *(_QWORD *)(onValueChanged_6 + 32) = v195; /*0x1d94a9c*/
  if ( !settingItemsManager_7 ) /*0x1d94aa0*/
    goto LABEL_125; /*0x1d94aa0*/
  SettingItemsManager__AddToggle_31033964( /*0x1d94ab8*/
    settingItemsManager_7,
    title_7,
    value_2,
    (UnityEngine_Events_UnityAction_bool__array *)onValueChanged_6,
    0);
  settingItemsManager_8 = this->fields.settingItemsManager; /*0x1d94ac0*/
  title_8 = (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)sub_1B624E8(Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo); /*0x1d94acc*/
  Phigros2_Utils_MultiLanguageText_MultiLanguageTextString___ctor(title_8, 0); /*0x1d94ad0*/
  if ( !title_8 ) /*0x1d94ad4*/
    goto LABEL_125; /*0x1d94ad4*/
  title_8->fields.chinese = (struct System_String_o *)StringLiteral_16120; /*0x1d94aec*/
  title_8->fields.chineseTraditional = (struct System_String_o *)StringLiteral_16122; /*0x1d94afc*/
  title_8->fields.english = (struct System_String_o *)StringLiteral_6199; /*0x1d94b0c*/
  StringLiteral_16603 = StringLiteral_16603; /*0x1d94b1c*/
  title_8->fields.japanese = (struct System_String_o *)StringLiteral_16121; /*0x1d94b20*/
  title_8->fields.korean = (struct System_String_o *)StringLiteral_16603; /*0x1d94b20*/
  _gameInformation_4 = this->fields._gameInformation; /*0x1d94b24*/
  if ( !_gameInformation_4 ) /*0x1d94b28*/
    goto LABEL_125; /*0x1d94b28*/
  isLowRes = _gameInformation_4->fields.isLowRes; /*0x1d94b34*/
  onValueChanged_7 = sub_1B62344(UnityEngine_Events_UnityAction_bool____TypeInfo, 1); /*0x1d94b48*/
  v202 = (UnityEngine_Events_UnityAction_bool__o *)sub_1B624E8(UnityEngine_Events_UnityAction_bool__TypeInfo); /*0x1d94b60*/
  UnityEngine_Events_UnityAction_bool____ctor(v202, (Il2CppObject *)this, Method_Setting_ChangeLowResMode__, 0); /*0x1d94b64*/
  if ( !onValueChanged_7 ) /*0x1d94b68*/
    goto LABEL_125; /*0x1d94b68*/
  if ( !*(_DWORD *)(onValueChanged_7 + 24) ) /*0x1d94b6c*/
    goto LABEL_126; /*0x1d94b6c*/
  *(_QWORD *)(onValueChanged_7 + 32) = v202; /*0x1d94b74*/
  if ( !settingItemsManager_8 ) /*0x1d94b78*/
    goto LABEL_125; /*0x1d94b78*/
  SettingItemsManager__AddToggle_31033964( /*0x1d94b9c*/
    settingItemsManager_8,
    title_8,
    isLowRes,
    (UnityEngine_Events_UnityAction_bool__array *)onValueChanged_7,
    0);
  if ( !SaveManagement__LoadInt((System_String_o *)StringLiteral_5716, 0, 0) ) /*0x1d94bac*/
  {
    Component_object = (__int64)this->fields.tutorialText; /*0x1d94bb4*/
    if ( !Component_object ) /*0x1d94bb8*/
      goto LABEL_125; /*0x1d94bb8*/
    Component_object = (__int64)UnityEngine_Component__get_transform((UnityEngine_Component_o *)Component_object, 0); /*0x1d94bc0*/
    if ( !Component_object ) /*0x1d94bc4*/
      goto LABEL_125; /*0x1d94bc4*/
    Component_object = (__int64)UnityEngine_Transform__get_parent((UnityEngine_Transform_o *)Component_object, 0); /*0x1d94bcc*/
    if ( !Component_object ) /*0x1d94bd0*/
      goto LABEL_125; /*0x1d94bd0*/
    Component_object = (__int64)UnityEngine_Component__get_gameObject((UnityEngine_Component_o *)Component_object, 0); /*0x1d94bd8*/
    if ( !Component_object ) /*0x1d94bdc*/
      goto LABEL_125; /*0x1d94bdc*/
    UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)Component_object, 0, 0); /*0x1d94be8*/
    Component_object = (__int64)this->fields.privacyPolicyText; /*0x1d94bec*/
    if ( !Component_object ) /*0x1d94bf0*/
      goto LABEL_125; /*0x1d94bf0*/
    Component_object = (__int64)UnityEngine_Component__get_transform((UnityEngine_Component_o *)Component_object, 0); /*0x1d94bf8*/
    if ( !Component_object ) /*0x1d94bfc*/
      goto LABEL_125; /*0x1d94bfc*/
    Component_object = (__int64)UnityEngine_Transform__get_parent((UnityEngine_Transform_o *)Component_object, 0); /*0x1d94c04*/
    if ( !Component_object ) /*0x1d94c08*/
      goto LABEL_125; /*0x1d94c08*/
    Component_object = (__int64)UnityEngine_Component__GetComponent_object_( /*0x1d94c18*/
                                  (UnityEngine_Component_o *)Component_object,
                                  (const MethodInfo_1F46C38 *)Method_UnityEngine_Component_GetComponent_RectTransform___);
    if ( !Component_object ) /*0x1d94c1c*/
      goto LABEL_125; /*0x1d94c1c*/
    value_3.fields.x = 0.0; /*0x1d94c20*/
    value_3.fields.y = 1.0; /*0x1d94c24*/
    Component_object_2 = (UnityEngine_RectTransform_o *)Component_object; /*0x1d94c2c*/
    UnityEngine_RectTransform__set_anchorMin((UnityEngine_RectTransform_o *)Component_object, value_3, 0); /*0x1d94c30*/
    value_4.fields.x = 0.5; /*0x1d94c34*/
    value_4.fields.y = 1.0; /*0x1d94c38*/
    UnityEngine_RectTransform__set_anchorMax(Component_object_2, value_4, 0); /*0x1d94c44*/
  }
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d94c58*/
    j_il2cpp_runtime_class_init_0(v203, v204, v205); /*0x1d94c6c*/
  v207 = SaveManagement__LoadString((System_String_o *)"GameCompleted", (System_String_o *)"", 0); /*0x1d94c7c*/
  if ( System_String__op_Inequality(v207, (System_String_o *)"4.0", 0) ) /*0x1d94c88*/
  {
    Component_object = (__int64)this->fields.aboutUsText; /*0x1d94c90*/
    if ( !Component_object ) /*0x1d94c94*/
      goto LABEL_125; /*0x1d94c94*/
    Component_object = (__int64)UnityEngine_Component__get_transform((UnityEngine_Component_o *)Component_object, 0); /*0x1d94c9c*/
    if ( !Component_object ) /*0x1d94ca0*/
      goto LABEL_125; /*0x1d94ca0*/
    Component_object = (__int64)UnityEngine_Transform__get_parent((UnityEngine_Transform_o *)Component_object, 0); /*0x1d94ca8*/
    if ( !Component_object ) /*0x1d94cac*/
      goto LABEL_125; /*0x1d94cac*/
    Component_object = (__int64)UnityEngine_Component__get_gameObject((UnityEngine_Component_o *)Component_object, 0); /*0x1d94cb4*/
    if ( !Component_object ) /*0x1d94cb8*/
      goto LABEL_125; /*0x1d94cb8*/
    UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)Component_object, 0, 0); /*0x1d94cc4*/
    Component_object = (__int64)this->fields.deleteAccountText; /*0x1d94cc8*/
    if ( !Component_object ) /*0x1d94ccc*/
      goto LABEL_125; /*0x1d94ccc*/
    Component_object = (__int64)UnityEngine_Component__get_transform((UnityEngine_Component_o *)Component_object, 0); /*0x1d94cd4*/
    if ( !Component_object ) /*0x1d94cd8*/
      goto LABEL_125; /*0x1d94cd8*/
    Component_object = (__int64)UnityEngine_Transform__get_parent((UnityEngine_Transform_o *)Component_object, 0); /*0x1d94ce0*/
    if ( !Component_object ) /*0x1d94ce4*/
      goto LABEL_125; /*0x1d94ce4*/
    Component_object = (__int64)UnityEngine_Component__GetComponent_object_( /*0x1d94cf4*/
                                  (UnityEngine_Component_o *)Component_object,
                                  (const MethodInfo_1F46C38 *)Method_UnityEngine_Component_GetComponent_RectTransform___);
    if ( !Component_object ) /*0x1d94cf8*/
      goto LABEL_125; /*0x1d94cf8*/
    value_5.fields.x = 0.0; /*0x1d94cfc*/
    value_5.fields.y = 1.0; /*0x1d94d00*/
    Component_object_3 = (UnityEngine_RectTransform_o *)Component_object; /*0x1d94d08*/
    UnityEngine_RectTransform__set_anchorMin((UnityEngine_RectTransform_o *)Component_object, value_5, 0); /*0x1d94d0c*/
    value_6.fields.x = 0.5; /*0x1d94d10*/
    value_6.fields.y = 1.0; /*0x1d94d14*/
    UnityEngine_RectTransform__set_anchorMax(Component_object_3, value_6, 0); /*0x1d94d20*/
  }
  Setting__RefreshDeleteAccountButtonState(this, method_1); /*0x1d94d28*/
  Component_object = (__int64)this->fields.versionText; /*0x1d94d2c*/
  if ( !Component_object ) /*0x1d94d30*/
    goto LABEL_125; /*0x1d94d30*/
  (*(void (__fastcall **)(__int64, __int64, _QWORD))(*(_QWORD *)Component_object + 1512LL))( /*0x1d94d54*/
    Component_object,
    StringLiteral_10134,
    *(_QWORD *)(*(_QWORD *)Component_object + 1520LL));
  deviceText = this->fields.deviceText; /*0x1d94d58*/
  deviceModel = UnityEngine_SystemInfo__get_deviceModel(0); /*0x1d94d60*/
  Component_object = (__int64)System_String__Concat_53436140((System_String_o *)StringLiteral_3932, deviceModel, 0); /*0x1d94d74*/
  if ( !deviceText ) /*0x1d94d78*/
    goto LABEL_125; /*0x1d94d78*/
  ((void (__fastcall *)(struct UnityEngine_UI_Text_o *, __int64, const MethodInfo *))deviceText->klass->vtable._75_set_text.methodPtr)( /*0x1d94d90*/
    deviceText,
    Component_object,
    deviceText->klass->vtable._75_set_text.method);
  audioFixText = this->fields.audioFixText; /*0x1d94d98*/
  v212 = (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)sub_1B624E8(Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo); /*0x1d94da4*/
  Phigros2_Utils_MultiLanguageText_MultiLanguageTextString___ctor(v212, 0); /*0x1d94da8*/
  if ( !v212 ) /*0x1d94dac*/
    goto LABEL_125; /*0x1d94dac*/
  v212->fields.chinese = (struct System_String_o *)StringLiteral_16484; /*0x1d94dcc*/
  v212->fields.chineseTraditional = (struct System_String_o *)StringLiteral_16483; /*0x1d94ddc*/
  v212->fields.english = (struct System_String_o *)StringLiteral_2300; /*0x1d94dec*/
  StringLiteral_16587 = StringLiteral_16587; /*0x1d94dfc*/
  v212->fields.japanese = (struct System_String_o *)StringLiteral_16481; /*0x1d94e00*/
  v212->fields.korean = (struct System_String_o *)StringLiteral_16587; /*0x1d94e00*/
  Component_object = (__int64)Phigros2_Utils_MultiLanguageText_MultiLanguageTextString__get_LocaleText(v212, 0); /*0x1d94e04*/
  if ( !audioFixText ) /*0x1d94e08*/
    goto LABEL_125; /*0x1d94e08*/
  ((void (__fastcall *)(struct UnityEngine_UI_Text_o *, __int64, const MethodInfo *))audioFixText->klass->vtable._75_set_text.methodPtr)( /*0x1d94e20*/
    audioFixText,
    Component_object,
    audioFixText->klass->vtable._75_set_text.method);
  privacyPolicyText = this->fields.privacyPolicyText; /*0x1d94e28*/
  v215 = (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)sub_1B624E8(Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo); /*0x1d94e34*/
  Phigros2_Utils_MultiLanguageText_MultiLanguageTextString___ctor(v215, 0); /*0x1d94e38*/
  if ( !v215 ) /*0x1d94e3c*/
    goto LABEL_125; /*0x1d94e3c*/
  v215->fields.chinese = (struct System_String_o *)StringLiteral_16469; /*0x1d94e5c*/
  v215->fields.chineseTraditional = (struct System_String_o *)StringLiteral_16471; /*0x1d94e6c*/
  v215->fields.english = (struct System_String_o *)StringLiteral_7283; /*0x1d94e7c*/
  StringLiteral_16505 = StringLiteral_16505; /*0x1d94e8c*/
  v215->fields.japanese = (struct System_String_o *)StringLiteral_16069; /*0x1d94e90*/
  v215->fields.korean = (struct System_String_o *)StringLiteral_16505; /*0x1d94e90*/
  Component_object = (__int64)Phigros2_Utils_MultiLanguageText_MultiLanguageTextString__get_LocaleText(v215, 0); /*0x1d94e94*/
  if ( !privacyPolicyText ) /*0x1d94e98*/
    goto LABEL_125; /*0x1d94e98*/
  ((void (__fastcall *)(struct UnityEngine_UI_Text_o *, __int64, const MethodInfo *))privacyPolicyText->klass->vtable._75_set_text.methodPtr)( /*0x1d94eb0*/
    privacyPolicyText,
    Component_object,
    privacyPolicyText->klass->vtable._75_set_text.method);
  licenseText = this->fields.licenseText; /*0x1d94eb8*/
  v218 = (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)sub_1B624E8(Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo); /*0x1d94ec4*/
  Phigros2_Utils_MultiLanguageText_MultiLanguageTextString___ctor(v218, 0); /*0x1d94ec8*/
  if ( !v218 ) /*0x1d94ecc*/
    goto LABEL_125; /*0x1d94ecc*/
  v218->fields.chinese = (struct System_String_o *)StringLiteral_16235; /*0x1d94eec*/
  v218->fields.chineseTraditional = (struct System_String_o *)StringLiteral_16465; /*0x1d94efc*/
  v218->fields.english = (struct System_String_o *)StringLiteral_6993; /*0x1d94f0c*/
  StringLiteral_16589 = StringLiteral_16589; /*0x1d94f1c*/
  v218->fields.japanese = (struct System_String_o *)StringLiteral_16034; /*0x1d94f20*/
  v218->fields.korean = (struct System_String_o *)StringLiteral_16589; /*0x1d94f20*/
  Component_object = (__int64)Phigros2_Utils_MultiLanguageText_MultiLanguageTextString__get_LocaleText(v218, 0); /*0x1d94f24*/
  if ( !licenseText ) /*0x1d94f28*/
    goto LABEL_125; /*0x1d94f28*/
  ((void (__fastcall *)(struct UnityEngine_UI_Text_o *, __int64, const MethodInfo *))licenseText->klass->vtable._75_set_text.methodPtr)( /*0x1d94f40*/
    licenseText,
    Component_object,
    licenseText->klass->vtable._75_set_text.method);
  tutorialText = this->fields.tutorialText; /*0x1d94f48*/
  v221 = (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)sub_1B624E8(Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo); /*0x1d94f54*/
  Phigros2_Utils_MultiLanguageText_MultiLanguageTextString___ctor(v221, 0); /*0x1d94f58*/
  if ( !v221 ) /*0x1d94f5c*/
    goto LABEL_125; /*0x1d94f5c*/
  v221->fields.chinese = (struct System_String_o *)StringLiteral_16420; /*0x1d94f7c*/
  v221->fields.chineseTraditional = (struct System_String_o *)StringLiteral_16419; /*0x1d94f8c*/
  v221->fields.english = (struct System_String_o *)StringLiteral_9483; /*0x1d94f9c*/
  StringLiteral_16638 = StringLiteral_16638; /*0x1d94fac*/
  v221->fields.japanese = (struct System_String_o *)StringLiteral_16060; /*0x1d94fb0*/
  v221->fields.korean = (struct System_String_o *)StringLiteral_16638; /*0x1d94fb0*/
  Component_object = (__int64)Phigros2_Utils_MultiLanguageText_MultiLanguageTextString__get_LocaleText(v221, 0); /*0x1d94fb4*/
  if ( !tutorialText ) /*0x1d94fb8*/
    goto LABEL_125; /*0x1d94fb8*/
  ((void (__fastcall *)(struct UnityEngine_UI_Text_o *, __int64, const MethodInfo *))tutorialText->klass->vtable._75_set_text.methodPtr)( /*0x1d94fd0*/
    tutorialText,
    Component_object,
    tutorialText->klass->vtable._75_set_text.method);
  aboutUsText = this->fields.aboutUsText; /*0x1d94fd8*/
  v224 = (Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_o *)sub_1B624E8(Phigros2_Utils_MultiLanguageText_MultiLanguageTextString_TypeInfo); /*0x1d94fe4*/
  Phigros2_Utils_MultiLanguageText_MultiLanguageTextString___ctor(v224, 0); /*0x1d94fe8*/
  if ( !v224 ) /*0x1d94fec*/
    goto LABEL_125; /*0x1d94fec*/
  v224->fields.chinese = (struct System_String_o *)StringLiteral_16140; /*0x1d9500c*/
  v224->fields.chineseTraditional = (struct System_String_o *)StringLiteral_16466; /*0x1d9501c*/
  v224->fields.english = (struct System_String_o *)StringLiteral_1907; /*0x1d9502c*/
  StringLiteral_16607 = StringLiteral_16607; /*0x1d9503c*/
  v224->fields.japanese = (struct System_String_o *)StringLiteral_7209; /*0x1d95040*/
  v224->fields.korean = (struct System_String_o *)StringLiteral_16607; /*0x1d95040*/
  Component_object = (__int64)Phigros2_Utils_MultiLanguageText_MultiLanguageTextString__get_LocaleText(v224, 0); /*0x1d95044*/
  if ( !aboutUsText ) /*0x1d95048*/
    goto LABEL_125; /*0x1d95048*/
  ((void (__fastcall *)(struct UnityEngine_UI_Text_o *, __int64, const MethodInfo *))aboutUsText->klass->vtable._75_set_text.methodPtr)( /*0x1d95060*/
    aboutUsText,
