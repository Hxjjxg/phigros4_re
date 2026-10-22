void RetributionEffect__Update(RetributionEffect_o *this, const MethodInfo *method)
{
  RetributionEffect_o *this_1; // x19
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  struct LevelControl_o *levelControl; // x8
  struct ProgressControl_o *progressControl; // x9
  float nowTime; // s0
  float v9; // s9
  float v10; // s0
  RetributionEffect_o *bgMask; // x19
  float v12; // s0
  float value_1; // s0
  double timeAffect; // d1
  double v15; // d0
  double v16; // d8
  struct JudgeControl_o *judgeControl; // x10
  struct LevelInformation_o *levelInformation; // x8
  Il2CppObject *redFlash; // x20
  System_Action_o *todo; // x20
  const MethodInfo *method_1; // x2
  struct LevelControl_o *levelControl_1; // x8
  struct ProgressControl_o *progressControl_1; // x8
  System_Collections_IEnumerator_o *routine; // x0
  double v25; // d10
  float displayHP; // s8
  float v27; // s0
  float v28; // s10
  bool v29; // nf
  float v30; // s0
  float v31; // s0
  float displayHP_2; // s1
  float displayHP_1; // s0
  float v34; // s8
  HPDisplayControl_c *HPDisplayControl_TypeInfo; // x8
  float v36; // s1
  struct HPDisplayControl_StaticFields *static_fields; // x9
  float v38; // s0
  float32x4_t _MainColor_k__BackingField; // q0
  float v40; // s0
  float v41; // s1
  float a; // s9
  struct HPDisplayControl_StaticFields *static_fields_1; // x9
  float v44; // s0
  float _GlowOpacity_k__BackingField; // s8
  float v46; // s0
  float value_2; // s1
  float value; // s0
  float32x4_t _MainColor_k__BackingField_1; // [xsp+0h] [xbp-70h]

  this_1 = this;
  if ( (byte_446E81D & 1) == 0 )
  {
    sub_1B622A0(&System_Action_TypeInfo, method);
    sub_1B622A0(&Method_UnityEngine_Object_Instantiate_GameObject___, v3);
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v4);
    this = (RetributionEffect_o *)sub_1B622A0(&Method_RetributionEffect__Update_b__20_0__, v5);
    byte_446E81D = 1;
  }
  levelControl = this_1->fields.levelControl;
  if ( !levelControl )
    goto LABEL_50;
  progressControl = levelControl->fields.progressControl;
  if ( !progressControl )
    goto LABEL_50;
  nowTime = progressControl->fields.nowTime;
  v9 = nowTime + -101.68;
  if ( (float)(nowTime + -101.68) >= 0.0 )
  {
    timeAffect = this_1->fields.timeAffect;
    v15 = this_1->fields.beginHP + timeAffect * -0.5 * v9 * v9;
    v16 = this_1->fields.totalNoteAffect + v15;
    if ( !this_1->fields.dead && v16 <= 0.0 )
    {
      this_1->fields.dead = 1;
      judgeControl = levelControl->fields.judgeControl;
      if ( !judgeControl )
        goto LABEL_50;
      judgeControl->fields.stopJudge = 1;
      levelInformation = levelControl->fields.levelInformation;
      if ( !levelInformation )
        goto LABEL_50;
      levelInformation->fields.canLevelOver = 0;
      redFlash = (Il2CppObject *)this_1->fields.redFlash;
      if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(*(float *)&v15, *(float *)&timeAffect, COERCE_FLOAT(COERCE_UNSIGNED_INT64(v9)));
      UnityEngine_Object__Instantiate_object__33364316(
        redFlash,
        (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
      todo = (System_Action_o *)sub_1B624E8(System_Action_TypeInfo);
      System_Action___ctor(todo, (Il2CppObject *)this_1, Method_RetributionEffect__Update_b__20_0__, 0);
      this = (RetributionEffect_o *)SimpleCoroutine__SimpleDelay((UnityEngine_MonoBehaviour_o *)this_1, 1.5, todo, 0);
      levelControl_1 = this_1->fields.levelControl;
      if ( !levelControl_1 )
        goto LABEL_50;
      progressControl_1 = levelControl_1->fields.progressControl;
      if ( !progressControl_1 )
        goto LABEL_50;
      routine = RetributionEffect__AudioFadeOut(this, progressControl_1->fields.audioSource, 1.0, method_1);
      UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)this_1, routine, 0);
    }
    v25 = 1.0;
    if ( v16 <= 1.0 )
      v25 = v16;
    else
      this_1->fields.totalNoteAffect = this_1->fields.totalNoteAffect - (v16 + -1.0);
    displayHP = this_1->fields.displayHP;
    v27 = UnityEngine_Time__get_deltaTime(0) * 6.0;
    v28 = v25;
    v29 = v27 < 0.0;
    v30 = fminf(v27, 1.0);
    if ( v29 )
      v30 = 0.0;
    v31 = displayHP + (float)((float)(v28 - displayHP) * v30);
    displayHP_2 = fminf(v31, 1.0);
    if ( v31 < 0.0 )
      displayHP_1 = 0.0;
    else
      displayHP_1 = displayHP_2;
    v34 = displayHP_1 * displayHP_1;
    this_1->fields.displayHP = displayHP_1;
    HPDisplayControl__set_HP(displayHP_1 * displayHP_1, 0);
    if ( !byte_446E86B )
    {
      sub_1B622A0(&HPDisplayControl_TypeInfo, method);
      byte_446E86B = 1;
    }
    HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
    v36 = fminf(v9 / 39.789, 1.0);
    static_fields = HPDisplayControl_TypeInfo->static_fields;
    if ( (float)(v9 / 39.789) < 0.0 )
      v38 = 0.0;
    else
      v38 = v36;
    static_fields->_Opacity_k__BackingField = (float)((float)((float)(1.0 - v34) * (float)(1.0 - v34)) * 0.5) + 0.1;
    _MainColor_k__BackingField = vaddq_f32(
                                   (float32x4_t)this_1->fields.startColor,
                                   vmulq_n_f32(
                                     vsubq_f32(
                                       (float32x4_t)this_1->fields.endColor,
                                       (float32x4_t)this_1->fields.startColor),
                                     v38));
    if ( !byte_446E86C )
    {
      _MainColor_k__BackingField_1 = _MainColor_k__BackingField;
      sub_1B622A0(&HPDisplayControl_TypeInfo, method);
      HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
      byte_446E86C = 1;
      _MainColor_k__BackingField = _MainColor_k__BackingField_1;
      static_fields = HPDisplayControl_TypeInfo->static_fields;
    }
    static_fields->_MainColor_k__BackingField = (struct UnityEngine_Color_o)_MainColor_k__BackingField;
    v40 = 1.0 - (float)(v34 + v34);
    v41 = fminf(v40, 1.0) * 0.5;
    if ( v40 < 0.0 )
      a = 0.0;
    else
      a = v41;
    if ( !byte_446E86D )
    {
      sub_1B622A0(&HPDisplayControl_TypeInfo, method);
      HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
      byte_446E86D = 1;
    }
    static_fields_1 = HPDisplayControl_TypeInfo->static_fields;
    static_fields_1->_CoverColor_k__BackingField.fields.a = a;
    *(_QWORD *)&static_fields_1->_CoverColor_k__BackingField.fields.r = 0;
    static_fields_1->_CoverColor_k__BackingField.fields.b = 0.0;
    v44 = v34 * 49.0;
    if ( v34 >= 0.02 )
      v44 = 1.0 - v34;
    _GlowOpacity_k__BackingField = v44 * 0.3;
    if ( !byte_446E86E )
    {
      sub_1B622A0(&HPDisplayControl_TypeInfo, method);
      HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
      byte_446E86E = 1;
    }
    HPDisplayControl_TypeInfo->static_fields->_GlowOpacity_k__BackingField = _GlowOpacity_k__BackingField;
    this = (RetributionEffect_o *)this_1->fields.uiCanvasGroup;
    if ( this )
    {
      v46 = (float)(v28 * 20.0) + -1.0;
      value_2 = fminf(v46, 1.0);
      v29 = v46 < 0.0;
      value = 0.0;
      if ( !v29 )
        value = value_2;
      UnityEngine_CanvasGroup__set_alpha((UnityEngine_CanvasGroup_o *)this, value, 0);
      this = (RetributionEffect_o *)this_1->fields.bgMask;
      if ( this )
      {
        value_1 = 1.0 - this_1->fields.displayHP;
        goto LABEL_49;
      }
    }
LABEL_50:
    sub_1B624F8(this, method);
  }
  v10 = nowTime + -82.105;
  if ( v10 <= 0.0 )
    return;
  this = (RetributionEffect_o *)this_1->fields.coverEffectCurve;
  if ( !this )
    goto LABEL_50;
  bgMask = (RetributionEffect_o *)this_1->fields.bgMask;
  v12 = UnityEngine_AnimationCurve__Evaluate((UnityEngine_AnimationCurve_o *)this, v10 / 19.579, 0);
  if ( !bgMask )
    goto LABEL_50;
  this = bgMask;
  value_1 = v12 * 0.83;
LABEL_49:
  UnityEngine_CanvasGroup__set_alpha((UnityEngine_CanvasGroup_o *)this, value_1, 0);
}
