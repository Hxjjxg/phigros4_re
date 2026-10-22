void RetributionEffectSecondPhase__Start(RetributionEffectSecondPhase_o *this, const MethodInfo *method)
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
  Il2CppObject *object; // x20
  UnityEngine_Transform_o *transform; // x0
  __int64 v21; // x1
  __int64 v22; // x2
  struct GameInformation_o *main; // x8
  struct LevelStartInfo_o *levelStartInfo; // x8
  System_String_o *songsLevel; // x21
  __int128 v26; // q0
  __int64 v27; // d1
  double timeAffect; // d2
  _BYTE *monitor; // x8
  UnityEngine_Transform_c *klass; // x23
  System_Delegate_o *rgctx_data; // x21
  System_Action_o *b; // x22
  System_Delegate_o *rgctx_data_1; // x0
  System_Action_c *System.Action_TypeInfo; // x1
  System_Delegate_o *typeHierarchy; // x21
  System_Action_o *b_1; // x22
  System_Action_c *System.Action_TypeInfo_1; // x1
  System_Delegate_o *unity_user_data; // x21
  System_Action_o *b_2; // x22
  System_Action_c *System.Action_TypeInfo_2; // x1
  System_Delegate_o *v41; // x21
  System_Action_o *b_3; // x22
  System_Action_c *System.Action_TypeInfo_3; // x1
  HPDisplayControl_c *HPDisplayControl_TypeInfo; // x8
  struct HPDisplayControl_StaticFields *static_fields; // x9
  struct HPDisplayControl_StaticFields *static_fields_1; // x9
  struct HPDisplayControl_StaticFields *static_fields_2; // x8
  UnityEngine_Transform_o *transform_1; // x21
  UnityEngine_GameObject_o *v49; // x20
  UnityEngine_Transform_o *transform_2; // x22
  struct LevelControl_o *levelControl; // x8
  struct LevelInformation_o *levelInformation; // x8
  RetributionEffectSecondPhase___c__DisplayClass24_0_o *v53; // x0
  const MethodInfo *method_1; // x1

  if ( (byte_446E821 & 1) == 0 )
  {
    sub_1B622A0(&System_Action_TypeInfo, method);
    sub_1B622A0(&Method_UnityEngine_Component_GetComponent_LevelControl___, v3);
    sub_1B622A0(&Method_UnityEngine_GameObject_AddComponent_CanvasGroup___, v4);
    sub_1B622A0(&Method_UnityEngine_GameObject_AddComponent_Image___, v5);
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_Image___, v6);
    sub_1B622A0(&UnityEngine_GameObject_TypeInfo, v7);
    sub_1B622A0(&Method_RetributionEffectSecondPhase___c__DisplayClass24_0__Start_b__0__, v8);
    sub_1B622A0(&Method_RetributionEffectSecondPhase___c__DisplayClass24_0__Start_b__1__, v9);
    sub_1B622A0(&Method_RetributionEffectSecondPhase___c__DisplayClass24_0__Start_b__2__, v10);
    sub_1B622A0(&Method_RetributionEffectSecondPhase___c__DisplayClass24_0__Start_b__3__, v11);
    sub_1B622A0(&RetributionEffectSecondPhase___c__DisplayClass24_0_TypeInfo, v12);
    sub_1B622A0(&"HD", v13);
    sub_1B622A0(&"BG Mask", v14);
    sub_1B622A0(&"IN", v15);
    sub_1B622A0(&"EZ", v16);
    sub_1B622A0(&"Pause", v17);
    sub_1B622A0(&"UI", v18);
    byte_446E821 = 1;
  }
  object = (Il2CppObject *)sub_1B624E8(RetributionEffectSecondPhase___c__DisplayClass24_0_TypeInfo);
  System_Object___ctor(object, 0);
  if ( !object )
    goto LABEL_52;
  object[1].klass = (Il2CppClass *)this;
  object[1].monitor = 0;
  object[2].klass = 0;
  object[2].monitor = 0;
  if ( !byte_446E372 )
  {
    transform = (UnityEngine_Transform_o *)sub_1B622A0(&GameInformation_TypeInfo, v21);
    byte_446E372 = 1;
  }
  main = GameInformation_TypeInfo->static_fields->_main;
  if ( !main )
    goto LABEL_52;
  levelStartInfo = main->fields.levelStartInfo;
  if ( !levelStartInfo )
    goto LABEL_52;
  songsLevel = levelStartInfo->fields.songsLevel;
  if ( System_String__op_Equality(songsLevel, (System_String_o *)"EZ", 0) )
  {
    v26 = xmmword_C34F80;
    v27 = 0x3F73A92A30553261LL;
    timeAffect = 0.000284977021;
LABEL_14:
    this->fields.timeAffect = timeAffect;
    object[2].monitor = (void *)v27;
    *(_OWORD *)&object[1].monitor = v26;
    goto LABEL_15;
  }
  if ( System_String__op_Equality(songsLevel, (System_String_o *)"HD", 0) )
  {
    v26 = xmmword_C351C0;
    v27 = 0x3F754C985F06F694LL;
    timeAffect = 0.000576304506;
    goto LABEL_14;
  }
  if ( System_String__op_Equality(songsLevel, (System_String_o *)"IN", 0) )
  {
    v26 = xmmword_C34EB0;
    v27 = 0x3F789374BC6A7EFALL;
    timeAffect = 0.000897002881;
    goto LABEL_14;
  }
LABEL_15:
  transform = UnityEngine_Component__get_transform((UnityEngine_Component_o *)this, 0);
  if ( !transform )
    goto LABEL_52;
  transform = UnityEngine_Transform__get_parent(transform, 0);
  if ( !transform )
    goto LABEL_52;
  transform = (UnityEngine_Transform_o *)UnityEngine_Component__GetComponent_object_(
                                           (UnityEngine_Component_o *)transform,
                                           (const MethodInfo_1F46C38 *)Method_UnityEngine_Component_GetComponent_LevelControl___);
  this->fields.levelControl = (struct LevelControl_o *)transform;
  if ( !transform )
    goto LABEL_52;
  monitor = transform[1].monitor;
  if ( !monitor )
    goto LABEL_52;
  monitor[107] = 0;
  klass = transform[2].klass;
  if ( !klass )
    goto LABEL_52;
  rgctx_data = (System_Delegate_o *)klass->rgctx_data;
  b = (System_Action_o *)sub_1B624E8(System_Action_TypeInfo);
  System_Action___ctor(b, object, Method_RetributionEffectSecondPhase___c__DisplayClass24_0__Start_b__0__, 0);
  rgctx_data_1 = System_Delegate__Combine(rgctx_data, (System_Delegate_o *)b, 0);
  System.Action_TypeInfo = System_Action_TypeInfo;
  if ( !rgctx_data_1 || (System_Action_c *)rgctx_data_1->klass == System_Action_TypeInfo )
  {
    typeHierarchy = (System_Delegate_o *)klass->_2.typeHierarchy;
    klass->rgctx_data = (Il2CppRGCTXData *)rgctx_data_1;
    b_1 = (System_Action_o *)sub_1B624E8(System.Action_TypeInfo);
    System_Action___ctor(b_1, object, Method_RetributionEffectSecondPhase___c__DisplayClass24_0__Start_b__1__, 0);
    rgctx_data_1 = System_Delegate__Combine(typeHierarchy, (System_Delegate_o *)b_1, 0);
    System.Action_TypeInfo_1 = System_Action_TypeInfo;
    if ( !rgctx_data_1 || (System_Action_c *)rgctx_data_1->klass == System_Action_TypeInfo )
    {
      unity_user_data = (System_Delegate_o *)klass->_2.unity_user_data;
      klass->_2.typeHierarchy = (Il2CppClass **)rgctx_data_1;
      b_2 = (System_Action_o *)sub_1B624E8(System.Action_TypeInfo_1);
      System_Action___ctor(b_2, object, Method_RetributionEffectSecondPhase___c__DisplayClass24_0__Start_b__2__, 0);
      rgctx_data_1 = System_Delegate__Combine(unity_user_data, (System_Delegate_o *)b_2, 0);
      System.Action_TypeInfo_2 = System_Action_TypeInfo;
      if ( !rgctx_data_1 || (System_Action_c *)rgctx_data_1->klass == System_Action_TypeInfo )
      {
        v41 = *(System_Delegate_o **)&klass->_2.initializationExceptionGCHandle;
        klass->_2.unity_user_data = rgctx_data_1;
        b_3 = (System_Action_o *)sub_1B624E8(System.Action_TypeInfo_2);
        System_Action___ctor(b_3, object, Method_RetributionEffectSecondPhase___c__DisplayClass24_0__Start_b__3__, 0);
        rgctx_data_1 = System_Delegate__Combine(v41, (System_Delegate_o *)b_3, 0);
        if ( !rgctx_data_1
          || (System.Action_TypeInfo_3 = System_Action_TypeInfo,
              (System_Action_c *)rgctx_data_1->klass == System_Action_TypeInfo) )
        {
          *(_QWORD *)&klass->_2.initializationExceptionGCHandle = rgctx_data_1;
          if ( !byte_446E86B )
          {
            sub_1B622A0(&HPDisplayControl_TypeInfo, System.Action_TypeInfo_3);
            byte_446E86B = 1;
          }
          HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
          static_fields = HPDisplayControl_TypeInfo->static_fields;
          static_fields->_Opacity_k__BackingField = 0.0;
          if ( !byte_446E86C )
          {
            sub_1B622A0(&HPDisplayControl_TypeInfo, System.Action_TypeInfo_3);
            HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
            byte_446E86C = 1;
            static_fields = HPDisplayControl_TypeInfo->static_fields;
          }
          *(_QWORD *)&static_fields->_MainColor_k__BackingField.fields.r = 0;
          *(_QWORD *)&static_fields->_MainColor_k__BackingField.fields.b = 0;
          if ( !byte_446E86D )
          {
            sub_1B622A0(&HPDisplayControl_TypeInfo, System.Action_TypeInfo_3);
            HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
            byte_446E86D = 1;
          }
          static_fields_1 = HPDisplayControl_TypeInfo->static_fields;
          *(_QWORD *)&static_fields_1->_CoverColor_k__BackingField.fields.r = 0;
          *(_QWORD *)&static_fields_1->_CoverColor_k__BackingField.fields.b = 0;
          if ( !byte_446E86E )
          {
            sub_1B622A0(&HPDisplayControl_TypeInfo, System.Action_TypeInfo_3);
            HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
            byte_446E86E = 1;
          }
          static_fields_2 = HPDisplayControl_TypeInfo->static_fields;
          static_fields_2->_GlowOpacity_k__BackingField = 0.0;
          if ( !byte_446E86F )
          {
            sub_1B622A0(&HPDisplayControl_TypeInfo, System.Action_TypeInfo_3);
            byte_446E86F = 1;
            static_fields_2 = HPDisplayControl_TypeInfo->static_fields;
          }
          static_fields_2->_LockSlash_k__BackingField = 0;
          transform = (UnityEngine_Transform_o *)UnityEngine_GameObject__Find((System_String_o *)"UI", 0);
          if ( transform )
          {
            transform = UnityEngine_GameObject__get_transform((UnityEngine_GameObject_o *)transform, 0);
            if ( transform )
            {
              transform_1 = transform;
              transform = UnityEngine_Transform__GetChild(transform, 1, 0);
              if ( transform )
              {
                transform = (UnityEngine_Transform_o *)UnityEngine_Component__get_gameObject(
                                                         (UnityEngine_Component_o *)transform,
                                                         0);
                if ( transform )
                {
                  this->fields.uiCanvasGroup = (struct UnityEngine_CanvasGroup_o *)UnityEngine_GameObject__AddComponent_object_(
                                                                                     (UnityEngine_GameObject_o *)transform,
                                                                                     (const MethodInfo_1F8E674 *)Method_UnityEngine_GameObject_AddComponent_CanvasGroup___);
                  v49 = (UnityEngine_GameObject_o *)sub_1B624E8(UnityEngine_GameObject_TypeInfo);
                  UnityEngine_GameObject___ctor(v49, (System_String_o *)"BG Mask", 0);
                  if ( v49 )
                  {
                    transform_2 = UnityEngine_GameObject__get_transform(v49, 0);
                    transform = UnityEngine_Transform__GetChild(transform_1, 0, 0);
                    if ( transform )
                    {
                      transform = UnityEngine_Transform__GetChild(transform, 0, 0);
                      if ( transform_2 )
                      {
                        UnityEngine_Transform__set_parent(transform_2, transform, 0);
                        transform = (UnityEngine_Transform_o *)UnityEngine_GameObject__AddComponent_object_(
                                                                 v49,
                                                                 (const MethodInfo_1F8E674 *)Method_UnityEngine_GameObject_AddComponent_Image___);
                        if ( transform )
                        {
                          (*(void (__fastcall **)(UnityEngine_Transform_o *, _QWORD, float, float, float, float))&transform->klass[1]._2.field_count)(
                            transform,
                            *(_QWORD *)&transform->klass[1]._2.interfaces_count,
                            0.0,
                            0.0,
                            0.0,
                            1.0);
                          transform = (UnityEngine_Transform_o *)UnityEngine_GameObject__AddComponent_object_(
                                                                   v49,
                                                                   (const MethodInfo_1F8E674 *)Method_UnityEngine_GameObject_AddComponent_CanvasGroup___);
                          this->fields.bgMask = (struct UnityEngine_CanvasGroup_o *)transform;
                          if ( transform )
                          {
                            UnityEngine_CanvasGroup__set_alpha((UnityEngine_CanvasGroup_o *)transform, 0.0, 0);
                            transform = (UnityEngine_Transform_o *)UnityEngine_GameObject__Find(
                                                                     (System_String_o *)"Pause",
                                                                     0);
                            if ( transform )
                            {
                              transform = (UnityEngine_Transform_o *)UnityEngine_GameObject__GetComponent_object_(
                                                                       (UnityEngine_GameObject_o *)transform,
                                                                       (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Image___);
                              if ( transform )
                              {
                                transform = (UnityEngine_Transform_o *)(*(__int64 (__fastcall **)(UnityEngine_Transform_o *, _QWORD, float, float, float, float))&transform->klass[1]._2.field_count)(
                                                                         transform,
                                                                         *(_QWORD *)&transform->klass[1]._2.interfaces_count,
                                                                         1.0,
                                                                         1.0,
                                                                         1.0,
                                                                         0.2);
                                levelControl = this->fields.levelControl;
                                if ( levelControl )
                                {
                                  levelInformation = levelControl->fields.levelInformation;
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
                      }
                    }
                  }
                }
              }
            }
          }
LABEL_52:
          sub_1B624F8(transform, v21, v22);
        }
      }
    }
  }
  v53 = (RetributionEffectSecondPhase___c__DisplayClass24_0_o *)sub_1B62898(rgctx_data_1);
  RetributionEffectSecondPhase___c__DisplayClass24_0___ctor(v53, method_1);
}
