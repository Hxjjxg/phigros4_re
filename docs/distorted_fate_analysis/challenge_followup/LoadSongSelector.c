void ChapterSelector__LoadSongSelector(ChapterSelector_o *this, System_String_o *chapterCode, const MethodInfo *method)
{
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  __int64 v9; // x1
  __int64 v10; // x1
  __int64 v11; // x1
  __int64 v12; // x1
  __int64 v13; // x1
  float v14; // s0
  float v15; // s1
  float v16; // s2
  __int64 v17; // x1
  struct Phigros2_VisionReplay_VisionReplay_StaticFields *static_fields; // x8
  SaturnOSLaunchControl_o *saturnOSLaunchControl; // x0
  const MethodInfo *method_1; // x2
  float v21; // s0
  float v22; // s1
  float v23; // s2
  float v24; // s0
  float v25; // s1
  float v26; // s2
  Il2CppObject *cutOut; // x19
  struct SEPlayer_play_o *Play; // x8

  if ( (byte_446E947 & 1) == 0 ) /*0x1d56100*/
  {
    sub_1B622A0(&GameInformation_TypeInfo, chapterCode); /*0x1d5610c*/
    sub_1B622A0(&Method_UnityEngine_Object_Instantiate_GameObject___, v5); /*0x1d56118*/
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v6); /*0x1d56124*/
    sub_1B622A0(&SEPlayer_TypeInfo, v7); /*0x1d56130*/
    sub_1B622A0(&SaveManagement_TypeInfo, v8); /*0x1d5613c*/
    sub_1B622A0(&StringLiteral_6264, v9); /*0x1d56148*/
    sub_1B622A0(&StringLiteral_6254, v10); /*0x1d56154*/
    sub_1B622A0(&StringLiteral_8037, v11); /*0x1d56160*/
    sub_1B622A0(&StringLiteral_5777, v12); /*0x1d5616c*/
    sub_1B622A0(&StringLiteral_5778, v13); /*0x1d56178*/
    byte_446E947 = 1; /*0x1d56180*/
  }
  if ( !this->fields.leave ) /*0x1d56184*/
  {
    this->fields.leave = 1; /*0x1d561a8*/
    GameInformation_TypeInfo->static_fields->nowChapterCode = chapterCode; /*0x1d561bc*/
    if ( System_String__op_Equality(chapterCode, (System_String_o *)StringLiteral_6254, 0) ) /*0x1d561cc*/
    {
      if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d561e0*/
        j_il2cpp_runtime_class_init_0(v14, v15, v16); /*0x1d561e8*/
      if ( SaveManagement__LoadBool((System_String_o *)StringLiteral_5777, 1, 0) ) /*0x1d56200*/
        goto LABEL_14; /*0x1d56204*/
      if ( !byte_446E376 ) /*0x1d56210*/
      {
        sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, v17); /*0x1d5621c*/
        byte_446E376 = 1; /*0x1d56224*/
      }
      static_fields = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields; /*0x1d56234*/
      if ( static_fields->_IsOn_k__BackingField ) /*0x1d56238*/
      {
        if ( !byte_446E377 ) /*0x1d56248*/
        {
          sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, v17); /*0x1d56250*/
          byte_446E377 = 1; /*0x1d5625c*/
          static_fields = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields; /*0x1d56260*/
        }
        if ( static_fields->_IsSimulateMode_k__BackingField ) /*0x1d56264*/
        {
LABEL_14:
          saturnOSLaunchControl = this->fields.saturnOSLaunchControl; /*0x1d5626c*/
          if ( saturnOSLaunchControl ) /*0x1d56270*/
          {
            SaturnOSLaunchControl__ToChapter8(saturnOSLaunchControl, 0); /*0x1d56284*/
            return; /*0x1d56284*/
          }
LABEL_26:
          sub_1B624F8(saturnOSLaunchControl, v17); /*0x1d56370*/
        }
      }
    }
    if ( !System_String__op_Equality(chapterCode, (System_String_o *)StringLiteral_6264, 0) ) /*0x1d562a0*/
      goto LABEL_22; /*0x1d562a0*/
    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d562b0*/
      j_il2cpp_runtime_class_init_0(v21, v22, v23); /*0x1d562b8*/
    if ( SaveManagement__LoadBool((System_String_o *)StringLiteral_5778, 1, 0) ) /*0x1d562d0*/
    {
      saturnOSLaunchControl = (SaturnOSLaunchControl_o *)this->fields.launchControl; /*0x1d562d8*/
      if ( !saturnOSLaunchControl ) /*0x1d562dc*/
        goto LABEL_26; /*0x1d562dc*/
      Phigros2_Chapter9_LaunchControl__ToChapter9((Phigros2_Chapter9_LaunchControl_o *)saturnOSLaunchControl, 0); /*0x1d562f0*/
    }
    else
    {
LABEL_22:
      ChapterSelector__StartNextScene(this, (System_String_o *)StringLiteral_8037, method_1); /*0x1d56304*/
      cutOut = (Il2CppObject *)this->fields.cutOut; /*0x1d56310*/
      if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1d56318*/
        j_il2cpp_runtime_class_init_0(v24, v25, v26); /*0x1d56320*/
      saturnOSLaunchControl = (SaturnOSLaunchControl_o *)UnityEngine_Object__Instantiate_object__33364316( /*0x1d56334*/
                                                           cutOut,
                                                           (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
      Play = SEPlayer_TypeInfo->static_fields->Play; /*0x1d56348*/
      if ( !Play ) /*0x1d5634c*/
        goto LABEL_26; /*0x1d5634c*/
      ((void (__fastcall *)(intptr_t, _QWORD, intptr_t))Play->fields.invoke_impl)( /*0x1d5636c*/
        Play->fields.method_code,
        0,
        Play->fields.method);
    }
  }
}
