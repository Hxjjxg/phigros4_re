void C9AfterDeductionControl__Update(C9AfterDeductionControl_o *this, const MethodInfo *method)
{
  __int64 v3; // x1
  float nowTime; // s8
  const MethodInfo *method_1; // x1
  float deltaTime; // s0
  int32x2_t v7; // d1
  struct UnityEngine_Vector2_o endPosition; // d3
  float nowTime_1; // s0
  UnityEngine_Transform_o *transform; // x0
  __int64 v11; // x1
  float v12; // s3
  struct UnityEngine_Vector2_o value; // [xsp+0h] [xbp-30h]
  UnityEngine_Vector3_o value_1; // 0:kr00_12.12

  if ( (byte_446E89C & 1) == 0 ) /*0x1d3fda8*/
  {
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_Text___, method); /*0x1d3fdb4*/
    sub_1B622A0(&"skip", v3); /*0x1d3fdc0*/
    byte_446E89C = 1; /*0x1d3fdc8*/
  }
  if ( !this->fields.playing ) /*0x1d3fdd0*/
    return; /*0x1d3fdd0*/
  nowTime = this->fields.nowTime; /*0x1d3fdd4*/
  deltaTime = UnityEngine_Time__get_deltaTime(0); /*0x1d3fddc*/
  v7.n64_u32[0] = LODWORD(this->fields.time); /*0x1d3fde0*/
  endPosition = this->fields.endPosition; /*0x1d3fde4*/
  nowTime_1 = nowTime + deltaTime; /*0x1d3fde8*/
  this->fields.nowTime = nowTime_1; /*0x1d3fdec*/
  if ( nowTime_1 < v7.n64_f32[0] ) /*0x1d3fdf4*/
    endPosition = (struct UnityEngine_Vector2_o)vadd_f32( /*0x1d3fe0c*/
                                                  (float32x2_t)this->fields.startPosition,
                                                  vdiv_f32(
                                                    vmul_n_f32(
                                                      vsub_f32(
                                                        (float32x2_t)endPosition,
                                                        (float32x2_t)this->fields.startPosition),
                                                      nowTime_1),
                                                    vdup_lane_s32(v7, 0))).n64_u64[0];
  if ( nowTime_1 > (float)(v7.n64_f32[0] + 6.0) ) /*0x1d3fe1c*/
    goto LABEL_7; /*0x1d3fe1c*/
  value = endPosition; /*0x1d3fe40*/
  transform = UnityEngine_Component__get_transform((UnityEngine_Component_o *)this, 0); /*0x1d3fe44*/
  if ( !transform ) /*0x1d3fe48*/
    goto LABEL_28; /*0x1d3fe48*/
  *(struct UnityEngine_Vector2_o *)&value_1.fields.x = value; /*0xf1c0000000000004*/
  value_1.fields.z = 0.0; /*0xf1c000000000000c*/
  UnityEngine_Transform__set_position(transform, value_1, 0); /*0x1d3fe5c*/
  if ( !this->fields.allowSkip ) /*0x1d3fe60*/
    goto LABEL_22; /*0x1d3fe60*/
  transform = (UnityEngine_Transform_o *)MyInput__get_touches(0); /*0x1d3fe6c*/
  if ( !transform ) /*0x1d3fe70*/
    goto LABEL_28; /*0x1d3fe70*/
  if ( !transform[1].klass ) /*0x1d3fe74*/
  {
LABEL_22:
    if ( !this->fields.cachedFingerStatus ) /*0x1d3ff18*/
      return; /*0x1d3ff18*/
    transform = (UnityEngine_Transform_o *)this->fields.anim; /*0x1d3ff1c*/
    this->fields.cachedFingerStatus = 0; /*0x1d3ff20*/
    if ( transform ) /*0x1d3ff24*/
    {
      transform = (UnityEngine_Transform_o *)UnityEngine_Animation__get_Item( /*0x1d3ff38*/
                                               (UnityEngine_Animation_o *)transform,
                                               (System_String_o *)"skip",
                                               0);
      if ( transform ) /*0x1d3ff3c*/
      {
        UnityEngine_AnimationState__set_normalizedTime((UnityEngine_AnimationState_o *)transform, 0.33, 0); /*0x1d3ff4c*/
        transform = (UnityEngine_Transform_o *)this->fields.anim; /*0x1d3ff50*/
        if ( transform ) /*0x1d3ff54*/
        {
          transform = (UnityEngine_Transform_o *)UnityEngine_Animation__get_Item( /*0x1d3ff60*/
                                                   (UnityEngine_Animation_o *)transform,
                                                   (System_String_o *)"skip",
                                                   0);
          if ( transform ) /*0x1d3ff64*/
          {
            UnityEngine_AnimationState__set_speed((UnityEngine_AnimationState_o *)transform, -1.0, 0); /*0x1d3ff80*/
            return; /*0x1d3ff80*/
          }
        }
      }
    }
    goto LABEL_28; /*0x1d3ff64*/
  }
  if ( !this->fields.cachedFingerStatus ) /*0x1d3fe7c*/
  {
    transform = (UnityEngine_Transform_o *)this->fields.anim; /*0x1d3fe84*/
    this->fields.cachedFingerStatus = 1; /*0x1d3fe8c*/
    if ( transform ) /*0x1d3fe90*/
    {
      transform = (UnityEngine_Transform_o *)UnityEngine_Animation__get_Item( /*0x1d3fea4*/
                                               (UnityEngine_Animation_o *)transform,
                                               (System_String_o *)"skip",
                                               0);
      if ( transform ) /*0x1d3fea8*/
      {
        UnityEngine_AnimationState__set_speed((UnityEngine_AnimationState_o *)transform, 1.0, 0); /*0x1d3feb4*/
        transform = (UnityEngine_Transform_o *)this->fields.anim; /*0x1d3feb8*/
        if ( transform ) /*0x1d3febc*/
        {
          UnityEngine_Animation__Play((UnityEngine_Animation_o *)transform, 0); /*0x1d3fec4*/
          goto LABEL_17; /*0x1d3fec4*/
        }
      }
    }
LABEL_28:
    sub_1B624F8(transform, v11); /*0x1d3ff98*/
  }
LABEL_17:
  transform = (UnityEngine_Transform_o *)this->fields.anim; /*0x1d3fec8*/
  if ( !transform ) /*0x1d3fecc*/
    goto LABEL_28; /*0x1d3fecc*/
  transform = (UnityEngine_Transform_o *)UnityEngine_Component__get_gameObject((UnityEngine_Component_o *)transform, 0); /*0x1d3fed4*/
  if ( !transform ) /*0x1d3fed8*/
    goto LABEL_28; /*0x1d3fed8*/
  transform = (UnityEngine_Transform_o *)UnityEngine_GameObject__GetComponent_object_( /*0x1d3fee8*/
                                           (UnityEngine_GameObject_o *)transform,
                                           (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Text___);
  if ( !transform ) /*0x1d3feec*/
    goto LABEL_28; /*0x1d3feec*/
  (*(void (__fastcall **)(UnityEngine_Transform_o *, _QWORD))&transform->klass[1]._2.thread_static_fields_offset)( /*0x1d3fefc*/
    transform,
    *(_QWORD *)&transform->klass[1]._2.token);
  if ( v12 > 0.99 ) /*0x1d3ff0c*/
LABEL_7:
    C9AfterDeductionControl__Back(this, method_1); /*0x1d3fe20*/
}
