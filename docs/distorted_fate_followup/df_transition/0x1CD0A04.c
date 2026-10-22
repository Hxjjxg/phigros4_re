void DFLevelEffect__Start(DFLevelEffect_o *this, const MethodInfo *method)
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
  __int64 dfShaderVariantCollection; // x0
  struct UnityEngine_Camera_o *main; // x0
  float v25; // s0
  float v26; // s1
  float v27; // s2
  UnityEngine_Object_o *main_1; // x20
  struct Exposure_Saturate_ContrastSettings_o *esc; // x9
  struct RadialBlurSettings_o *radialBlur; // x9
  struct BloomSettings_o *bloom; // x9
  struct VignettePlusSettings_o *vignettePlus; // x9
  __int64 v33; // x8
  Il2CppObject *countDown; // x20
  UnityEngine_Transform_o *transform; // x21
  float v36; // s0
  float v37; // s1
  float v38; // s2
  struct LevelControl_o *levelControl; // x8
  struct LevelInformation_o *levelInformation; // x19
  UnityEngine_Object_o *gameObject; // x19
  float v42; // s0
  float v43; // s1
  float v44; // s2

  if ( (byte_446E410 & 1) == 0 ) /*0x1cd0a18*/
  {
    sub_1B622A0(&Method_UnityEngine_Component_GetComponentInParent_LevelControl___, method); /*0x1cd0a24*/
    sub_1B622A0(&Method_UnityEngine_GameObject_AddComponent_PostProcessingManager___, v3); /*0x1cd0a30*/
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponentInChildren_Canvas___, v4); /*0x1cd0a3c*/
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponentInChildren_Image___, v5); /*0x1cd0a48*/
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_BlackMaskControl___, v6); /*0x1cd0a54*/
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_Canvas___, v7); /*0x1cd0a60*/
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_CountDownTimeControl___, v8); /*0x1cd0a6c*/
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_Image___, v9); /*0x1cd0a78*/
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_RectTransform___, v10); /*0x1cd0a84*/
    sub_1B622A0(&Method_UnityEngine_Object_Instantiate_GameObject___, v11); /*0x1cd0a90*/
    sub_1B622A0(&Method_UnityEngine_Object_Instantiate_GameObject____69817432, v12); /*0x1cd0a9c*/
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v13); /*0x1cd0aa8*/
    sub_1B622A0(&"Combo", v14); /*0x1cd0ab4*/
    sub_1B622A0(&"Score", v15); /*0x1cd0ac0*/
    sub_1B622A0(&"ComboText", v16); /*0x1cd0acc*/
    sub_1B622A0(&"Pause", v17); /*0x1cd0ad8*/
    sub_1B622A0(&"SongsLevel", v18); /*0x1cd0ae4*/
    sub_1B622A0(&"Canvas (1)", v19); /*0x1cd0af0*/
    sub_1B622A0(&"Progress Bar", v20); /*0x1cd0afc*/
    sub_1B622A0(&"Edge", v21); /*0x1cd0b08*/
    sub_1B622A0(&"SongsName", v22); /*0x1cd0b14*/
    byte_446E410 = 1; /*0x1cd0b1c*/
  }
  dfShaderVariantCollection = (__int64)this->fields.dfShaderVariantCollection; /*0x1cd0b20*/
  if ( !dfShaderVariantCollection ) /*0x1cd0b24*/
    goto LABEL_50; /*0x1cd0b24*/
  UnityEngine_ShaderVariantCollection__WarmUp((UnityEngine_ShaderVariantCollection_o *)dfShaderVariantCollection, 0); /*0x1cd0b34*/
  main = UnityEngine_Camera__get_main(0); /*0x1cd0b3c*/
  this->fields._mainCamera = main; /*0x1cd0b40*/
  main_1 = (UnityEngine_Object_o *)main; /*0x1cd0b44*/
  if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1cd0b4c*/
    j_il2cpp_runtime_class_init_0(v25, v26, v27); /*0x1cd0b54*/
  if ( UnityEngine_Object__op_Inequality(main_1, 0, 0) ) /*0x1cd0b64*/
  {
    dfShaderVariantCollection = (__int64)this->fields._mainCamera; /*0x1cd0b6c*/
    if ( dfShaderVariantCollection ) /*0x1cd0b70*/
    {
      dfShaderVariantCollection = (__int64)UnityEngine_Component__get_gameObject( /*0x1cd0b78*/
                                             (UnityEngine_Component_o *)dfShaderVariantCollection,
                                             0);
      if ( dfShaderVariantCollection ) /*0x1cd0b7c*/
      {
        dfShaderVariantCollection = (__int64)UnityEngine_GameObject__AddComponent_object_( /*0x1cd0b8c*/
                                               (UnityEngine_GameObject_o *)dfShaderVariantCollection,
                                               (const MethodInfo_1F8E674 *)Method_UnityEngine_GameObject_AddComponent_PostProcessingManager___);
        this->fields._postProcessingManager = (struct PostProcessingManager_o *)dfShaderVariantCollection; /*0x1cd0b90*/
        if ( dfShaderVariantCollection ) /*0x1cd0b94*/
        {
          PostProcessingManager__ClearEffects((PostProcessingManager_o *)dfShaderVariantCollection, 0); /*0x1cd0b9c*/
          dfShaderVariantCollection = (__int64)this->fields._postProcessingManager; /*0x1cd0ba0*/
          if ( dfShaderVariantCollection ) /*0x1cd0ba4*/
          {
            PostProcessingManager__AddEffects( /*0x1cd0bb0*/
              (PostProcessingManager_o *)dfShaderVariantCollection,
              (PostEffectSettings_o *)this->fields.bloom,
              0);
            dfShaderVariantCollection = (__int64)this->fields._postProcessingManager; /*0x1cd0bb4*/
            if ( dfShaderVariantCollection ) /*0x1cd0bb8*/
            {
              PostProcessingManager__AddEffects( /*0x1cd0bc4*/
                (PostProcessingManager_o *)dfShaderVariantCollection,
                (PostEffectSettings_o *)this->fields.radialBlur,
                0);
              dfShaderVariantCollection = (__int64)this->fields._postProcessingManager; /*0x1cd0bc8*/
              if ( dfShaderVariantCollection ) /*0x1cd0bcc*/
              {
                PostProcessingManager__AddEffects( /*0x1cd0bd8*/
                  (PostProcessingManager_o *)dfShaderVariantCollection,
                  (PostEffectSettings_o *)this->fields.vignettePlus,
                  0);
                dfShaderVariantCollection = (__int64)this->fields._postProcessingManager; /*0x1cd0bdc*/
                if ( dfShaderVariantCollection ) /*0x1cd0be0*/
                {
                  PostProcessingManager__AddEffects( /*0x1cd0bec*/
                    (PostProcessingManager_o *)dfShaderVariantCollection,
                    (PostEffectSettings_o *)this->fields.esc,
                    0);
                  esc = this->fields.esc; /*0x1cd0bf0*/
                  if ( esc ) /*0x1cd0bf4*/
                  {
                    esc->fields.enabled = 1; /*0x1cd0bfc*/
                    radialBlur = this->fields.radialBlur; /*0x1cd0c00*/
                    if ( radialBlur ) /*0x1cd0c04*/
                    {
                      radialBlur->fields.enabled = 1; /*0x1cd0c08*/
                      bloom = this->fields.bloom; /*0x1cd0c0c*/
                      if ( bloom ) /*0x1cd0c10*/
                      {
                        bloom->fields.enabled = 1; /*0x1cd0c18*/
                        vignettePlus = this->fields.vignettePlus; /*0x1cd0c1c*/
                        if ( vignettePlus ) /*0x1cd0c20*/
                        {
                          vignettePlus->fields.enabled = 1; /*0x1cd0c24*/
                          dfShaderVariantCollection = (__int64)UnityEngine_Component__GetComponentInParent_object_( /*0x1cd0c38*/
                                                                 (UnityEngine_Component_o *)this,
                                                                 (const MethodInfo_1F472F4 *)Method_UnityEngine_Component_GetComponentInParent_LevelControl___);
                          this->fields._levelControl = (struct LevelControl_o *)dfShaderVariantCollection; /*0x1cd0c3c*/
                          if ( dfShaderVariantCollection ) /*0x1cd0c40*/
                          {
                            this->fields._progressControl = *(struct ProgressControl_o **)(dfShaderVariantCollection + 40); /*0x1cd0c48*/
                            v33 = *(_QWORD *)(dfShaderVariantCollection + 32); /*0x1cd0c4c*/
                            if ( v33 ) /*0x1cd0c50*/
                            {
                              *(_BYTE *)(v33 + 107) = 0; /*0x1cd0c54*/
                              this->fields._pause = UnityEngine_GameObject__Find( /*0x1cd0c6c*/
                                                      (System_String_o *)"Pause",
                                                      0);
                              this->fields._progressBar = UnityEngine_GameObject__Find( /*0x1cd0c84*/
                                                            (System_String_o *)"Progress Bar",
                                                            0);
                              this->fields._score = UnityEngine_GameObject__Find( /*0x1cd0c9c*/
                                                      (System_String_o *)"Score",
                                                      0);
                              this->fields._songsLevel = UnityEngine_GameObject__Find( /*0x1cd0cb4*/
                                                           (System_String_o *)"SongsLevel",
                                                           0);
                              this->fields._songsName = UnityEngine_GameObject__Find( /*0x1cd0ccc*/
                                                          (System_String_o *)"SongsName",
                                                          0);
                              this->fields._combo = UnityEngine_GameObject__Find( /*0x1cd0ce4*/
                                                      (System_String_o *)"Combo",
                                                      0);
                              this->fields._comboText = UnityEngine_GameObject__Find( /*0x1cd0cfc*/
                                                          (System_String_o *)"ComboText",
                                                          0);
                              dfShaderVariantCollection = (__int64)UnityEngine_GameObject__Find( /*0x1cd0d10*/
                                                                     (System_String_o *)"Canvas (1)",
                                                                     0);
                              this->fields._uiCanvas = (struct UnityEngine_GameObject_o *)dfShaderVariantCollection; /*0x1cd0d14*/
                              if ( dfShaderVariantCollection ) /*0x1cd0d18*/
                              {
                                countDown = (Il2CppObject *)this->fields.countDown; /*0x1cd0d1c*/
                                transform = UnityEngine_GameObject__get_transform( /*0x1cd0d2c*/
                                              (UnityEngine_GameObject_o *)dfShaderVariantCollection,
                                              0);
                                if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1cd0d30*/
                                  j_il2cpp_runtime_class_init_0(v36, v37, v38); /*0x1cd0d3c*/
                                dfShaderVariantCollection = (__int64)UnityEngine_Object__Instantiate_object__33364932( /*0x1cd0d54*/
                                                                       countDown,
                                                                       transform,
                                                                       (const MethodInfo_1FD1BC4 *)Method_UnityEngine_Object_Instantiate_GameObject____69817432);
                                this->fields.countDown = (struct UnityEngine_GameObject_o *)dfShaderVariantCollection; /*0x1cd0d58*/
                                if ( dfShaderVariantCollection ) /*0x1cd0d5c*/
                                {
                                  UnityEngine_GameObject__SetActive( /*0x1cd0d68*/
                                    (UnityEngine_GameObject_o *)dfShaderVariantCollection,
                                    0,
                                    0);
                                  dfShaderVariantCollection = (__int64)this->fields.countDown; /*0x1cd0d6c*/
                                  if ( dfShaderVariantCollection ) /*0x1cd0d70*/
                                  {
                                    this->fields._countDownTimeControl = (struct CountDownTimeControl_o *)UnityEngine_GameObject__GetComponent_object_((UnityEngine_GameObject_o *)dfShaderVariantCollection, (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_CountDownTimeControl___); /*0x1cd0d84*/
                                    dfShaderVariantCollection = (__int64)UnityEngine_Object__Instantiate_object__33364316( /*0x1cd0d98*/
                                                                           (Il2CppObject *)this->fields.blackBg,
                                                                           (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
                                    this->fields.blackBg = (struct UnityEngine_GameObject_o *)dfShaderVariantCollection; /*0x1cd0d9c*/
                                    if ( dfShaderVariantCollection ) /*0x1cd0da0*/
                                    {
                                      dfShaderVariantCollection = (__int64)UnityEngine_GameObject__GetComponent_object_( /*0x1cd0db0*/
                                                                             (UnityEngine_GameObject_o *)dfShaderVariantCollection,
                                                                             (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Canvas___);
                                      if ( dfShaderVariantCollection ) /*0x1cd0db4*/
                                      {
                                        UnityEngine_Canvas__set_worldCamera( /*0x1cd0dc0*/
                                          (UnityEngine_Canvas_o *)dfShaderVariantCollection,
                                          this->fields._mainCamera,
                                          0);
                                        dfShaderVariantCollection = (__int64)this->fields.blackBg; /*0x1cd0dc4*/
                                        if ( dfShaderVariantCollection ) /*0x1cd0dc8*/
                                        {
                                          this->fields._blackBgImage = (struct UnityEngine_UI_Image_o *)UnityEngine_GameObject__GetComponentInChildren_object_((UnityEngine_GameObject_o *)dfShaderVariantCollection, (const MethodInfo_1F8EABC *)Method_UnityEngine_GameObject_GetComponentInChildren_Image___); /*0x1cd0ddc*/
                                          dfShaderVariantCollection = (__int64)UnityEngine_Object__Instantiate_object__33364316( /*0x1cd0de8*/
                                                                                 (Il2CppObject *)this->fields.grid,
                                                                                 (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
                                          this->fields.grid = (struct UnityEngine_GameObject_o *)dfShaderVariantCollection; /*0x1cd0dec*/
                                          if ( dfShaderVariantCollection ) /*0x1cd0df0*/
                                          {
                                            dfShaderVariantCollection = (__int64)UnityEngine_GameObject__GetComponentInChildren_object_( /*0x1cd0e00*/
                                                                                   (UnityEngine_GameObject_o *)dfShaderVariantCollection,
                                                                                   (const MethodInfo_1F8EABC *)Method_UnityEngine_GameObject_GetComponentInChildren_Canvas___);
                                            if ( dfShaderVariantCollection ) /*0x1cd0e04*/
                                            {
                                              UnityEngine_Canvas__set_worldCamera( /*0x1cd0e10*/
                                                (UnityEngine_Canvas_o *)dfShaderVariantCollection,
                                                this->fields._mainCamera,
                                                0);
                                              dfShaderVariantCollection = (__int64)UnityEngine_Object__Instantiate_object__33364316( /*0x1cd0e1c*/
                                                                                     (Il2CppObject *)this->fields.edge,
                                                                                     (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
                                              this->fields.edge = (struct UnityEngine_GameObject_o *)dfShaderVariantCollection; /*0x1cd0e20*/
                                              if ( dfShaderVariantCollection ) /*0x1cd0e24*/
                                              {
                                                dfShaderVariantCollection = (__int64)UnityEngine_GameObject__GetComponent_object_( /*0x1cd0e2c*/
                                                                                       (UnityEngine_GameObject_o *)dfShaderVariantCollection,
                                                                                       (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Canvas___);
                                                if ( dfShaderVariantCollection ) /*0x1cd0e30*/
                                                {
                                                  UnityEngine_Canvas__set_worldCamera( /*0x1cd0e3c*/
                                                    (UnityEngine_Canvas_o *)dfShaderVariantCollection,
                                                    this->fields._mainCamera,
                                                    0);
                                                  dfShaderVariantCollection = (__int64)UnityEngine_GameObject__Find( /*0x1cd0e50*/
                                                                                         (System_String_o *)"Edge",
                                                                                         0);
                                                  if ( dfShaderVariantCollection ) /*0x1cd0e54*/
                                                  {
                                                    this->fields._edgeTransform = (struct UnityEngine_RectTransform_o *)UnityEngine_GameObject__GetComponent_object_((UnityEngine_GameObject_o *)dfShaderVariantCollection, (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_RectTransform___); /*0x1cd0e68*/
                                                    dfShaderVariantCollection = (__int64)UnityEngine_Object__Instantiate_object__33364316( /*0x1cd0e74*/
                                                                                           (Il2CppObject *)this->fields.blackMask,
                                                                                           (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
                                                    this->fields.blackMask = (struct UnityEngine_GameObject_o *)dfShaderVariantCollection; /*0x1cd0e78*/
                                                    if ( dfShaderVariantCollection ) /*0x1cd0e7c*/
                                                    {
                                                      dfShaderVariantCollection = (__int64)UnityEngine_GameObject__GetComponent_object_( /*0x1cd0e84*/
                                                                                             (UnityEngine_GameObject_o *)dfShaderVariantCollection,
                                                                                             (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Canvas___);
                                                      if ( dfShaderVariantCollection ) /*0x1cd0e88*/
                                                      {
                                                        UnityEngine_Canvas__set_worldCamera( /*0x1cd0e94*/
                                                          (UnityEngine_Canvas_o *)dfShaderVariantCollection,
                                                          this->fields._mainCamera,
                                                          0);
                                                        dfShaderVariantCollection = (__int64)this->fields.blackMask; /*0x1cd0e98*/
                                                        if ( dfShaderVariantCollection ) /*0x1cd0e9c*/
                                                        {
                                                          dfShaderVariantCollection = (__int64)UnityEngine_GameObject__GetComponent_object_( /*0x1cd0eac*/
                                                                                                 (UnityEngine_GameObject_o *)dfShaderVariantCollection,
                                                                                                 (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_BlackMaskControl___);
                                                          this->fields._blackMaskControl = (struct BlackMaskControl_o *)dfShaderVariantCollection; /*0x1cd0eb0*/
                                                          if ( !byte_446E376 ) /*0x1cd0ebc*/
                                                          {
                                                            dfShaderVariantCollection = sub_1B622A0( /*0x1cd0ec8*/
                                                                                          &Phigros2_VisionReplay_VisionReplay_TypeInfo,
                                                                                          method);
                                                            byte_446E376 = 1; /*0x1cd0ed0*/
                                                          }
                                                          if ( !Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields->_IsOn_k__BackingField ) /*0x1cd0ee4*/
                                                          {
                                                            dfShaderVariantCollection = (__int64)this->fields._pause; /*0x1cd0eec*/
                                                            if ( !dfShaderVariantCollection ) /*0x1cd0ef0*/
                                                              goto LABEL_50; /*0x1cd0ef0*/
                                                            dfShaderVariantCollection = (__int64)UnityEngine_GameObject__GetComponent_object_( /*0x1cd0f00*/
                                                                                                   (UnityEngine_GameObject_o *)dfShaderVariantCollection,
                                                                                                   (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Image___);
                                                            if ( !dfShaderVariantCollection ) /*0x1cd0f04*/
                                                              goto LABEL_50; /*0x1cd0f04*/
                                                            dfShaderVariantCollection = (*(__int64 (__fastcall **)(__int64, _QWORD, float, float, float, float))(*(_QWORD *)dfShaderVariantCollection + 680LL))( /*0x1cd0f28*/
                                                                                          dfShaderVariantCollection,
                                                                                          *(_QWORD *)(*(_QWORD *)dfShaderVariantCollection + 688LL),
                                                                                          1.0,
                                                                                          1.0,
                                                                                          1.0,
                                                                                          0.2);
                                                          }
                                                          levelControl = this->fields._levelControl; /*0x1cd0f2c*/
                                                          if ( levelControl ) /*0x1cd0f30*/
                                                          {
                                                            levelInformation = levelControl->fields.levelInformation; /*0x1cd0f38*/
                                                            if ( !byte_446E376 ) /*0x1cd0f3c*/
                                                            {
                                                              dfShaderVariantCollection = sub_1B622A0( /*0x1cd0f48*/
                                                                                            &Phigros2_VisionReplay_VisionReplay_TypeInfo,
                                                                                            method);
                                                              byte_446E376 = 1; /*0x1cd0f50*/
                                                            }
                                                            if ( levelInformation ) /*0x1cd0f54*/
                                                            {
                                                              levelInformation->fields.canPause = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields->_IsOn_k__BackingField; /*0x1cd0f64*/
                                                              return; /*0x1cd0f70*/
                                                            }
                                                          }
                                                        }
                                                      }
                                                    }
                                                  }
                                                }
                                              }
                                            }
                                          }
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
LABEL_50:
    sub_1B624F8(dfShaderVariantCollection, method); /*0x1cd0fac*/
  }
  gameObject = (UnityEngine_Object_o *)UnityEngine_Component__get_gameObject((UnityEngine_Component_o *)this, 0); /*0x1cd0f84*/
  if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1cd0f88*/
    j_il2cpp_runtime_class_init_0(v42, v43, v44); /*0x1cd0f94*/
  UnityEngine_Object__Destroy_63300388(gameObject, 0); /*0x1cd0fa8*/
}