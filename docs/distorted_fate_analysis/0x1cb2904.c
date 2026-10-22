void AboutUs__Update(AboutUs_o *this, const MethodInfo *method)
{
  AboutUs_o *this_1; // x19
  __int64 v3; // x1
  struct AddressableAudioSource_o *audioSource; // x8
  struct AddressableAudioSource_o *audioSource_1; // x8
  AddressableAudioSource_o *audioSource_2; // x20
  float Volume; // s8
  float deltaTime; // s0
  float nowTime; // s8
  float deltaTime_1; // s0
  int32x2_t v11; // d1
  float32x2_t v12; // d3
  float nowTime_1; // s0
  float clip1Time; // s1
  float nowTime_2; // s1
  float clip1Time_1; // s1
  float clip2Time; // s2
  float v18; // s3
  unsigned __int64 value; // [xsp+0h] [xbp-30h]
  UnityEngine_Vector3_o value_1; // 0:kr00_12.12

  this_1 = this;
  if ( (byte_446E31D & 1) == 0 )
  {
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_Text___, method);
    this = (AboutUs_o *)sub_1B622A0(&"skip", v3);
    byte_446E31D = 1;
  }
  if ( this_1->fields.playing )
  {
    audioSource = this_1->fields.audioSource;
    if ( !audioSource )
      goto LABEL_46;
    this = (AboutUs_o *)System_String__op_Inequality(audioSource->fields.clipKey, this_1->fields.clip1, 0);
    if ( ((unsigned __int8)this & 1) != 0 )
    {
      audioSource_1 = this_1->fields.audioSource;
      if ( !audioSource_1 )
        goto LABEL_46;
      this = (AboutUs_o *)System_String__op_Inequality(audioSource_1->fields.clipKey, this_1->fields.clip2, 0);
      if ( ((unsigned __int8)this & 1) != 0 )
      {
        audioSource_2 = this_1->fields.audioSource;
        if ( !audioSource_2 )
          goto LABEL_46;
        Volume = AddressableAudioSource__get_Volume(this_1->fields.audioSource, 0);
        deltaTime = UnityEngine_Time__get_deltaTime(0);
        AddressableAudioSource__set_Volume(audioSource_2, Volume + (float)(deltaTime * -1.2), 0);
        this = (AboutUs_o *)this_1->fields.audioSource;
        if ( !this )
          goto LABEL_46;
        if ( AddressableAudioSource__get_Volume((AddressableAudioSource_o *)this, 0) <= 0.02 )
        {
          this = (AboutUs_o *)this_1->fields.audioSource;
          if ( !this )
            goto LABEL_46;
          AddressableAudioSource__set_Clip((AddressableAudioSource_o *)this, this_1->fields.clip1, 0);
          this = (AboutUs_o *)this_1->fields.audioSource;
          if ( !this )
            goto LABEL_46;
          AddressableAudioSource__Play((AddressableAudioSource_o *)this, 0);
          this = (AboutUs_o *)this_1->fields.audioSource;
          if ( !this )
            goto LABEL_46;
          AddressableAudioSource__set_Volume((AddressableAudioSource_o *)this, 1.0, 0);
        }
      }
    }
    nowTime = this_1->fields.nowTime;
    deltaTime_1 = UnityEngine_Time__get_deltaTime(0);
    v11.n64_u32[0] = LODWORD(this_1->fields.time);
    v12.n64_u64[0] = (unsigned __int64)this_1->fields.endPosition;
    nowTime_1 = nowTime + deltaTime_1;
    this_1->fields.nowTime = nowTime_1;
    if ( nowTime_1 < v11.n64_f32[0] )
      v12.n64_u64[0] = vadd_f32(
                         (float32x2_t)this_1->fields.startPosition,
                         vdiv_f32(
                           vmul_n_f32(vsub_f32(v12, (float32x2_t)this_1->fields.startPosition), nowTime_1),
                           vdup_lane_s32(v11, 0))).n64_u64[0];
    clip1Time = this_1->fields.clip1Time;
    value = v12.n64_u64[0];
    if ( nowTime_1 > clip1Time && this_1->fields.clip1 )
    {
      this = (AboutUs_o *)this_1->fields.audioSource;
      if ( !this )
        goto LABEL_46;
      AddressableAudioSource__set_Clip((AddressableAudioSource_o *)this, this_1->fields.clip2, 0);
      this = (AboutUs_o *)this_1->fields.audioSource;
      if ( !this )
        goto LABEL_46;
      AddressableAudioSource__Play((AddressableAudioSource_o *)this, 0);
      nowTime_1 = this_1->fields.nowTime;
      clip1Time = this_1->fields.clip1Time;
      this_1->fields.clip1 = 0;
    }
    nowTime_2 = clip1Time + this_1->fields.clip2Time;
    if ( nowTime_1 > nowTime_2 && this_1->fields.clip2 )
    {
      this = (AboutUs_o *)this_1->fields.audioSource;
      if ( !this )
        goto LABEL_46;
      AddressableAudioSource__Pause((AddressableAudioSource_o *)this, 0);
      clip1Time_1 = this_1->fields.clip1Time;
      clip2Time = this_1->fields.clip2Time;
      nowTime_1 = this_1->fields.nowTime;
      this_1->fields.clip2 = 0;
      nowTime_2 = clip1Time_1 + clip2Time;
    }
    if ( nowTime_1 > (float)(nowTime_2 + 6.0) )
    {
LABEL_39:
      AboutUs__Back(this_1, method);
      return;
    }
    this = (AboutUs_o *)UnityEngine_Component__get_transform((UnityEngine_Component_o *)this_1, 0);
    if ( !this
      || (*(_QWORD *)&value_1.fields.x = value,
          value_1.fields.z = 0.0,
          UnityEngine_Transform__set_position((UnityEngine_Transform_o *)this, value_1, 0),
          (this = (AboutUs_o *)MyInput__get_touches(0)) == 0) )
    {
LABEL_46:
      sub_1B624F8(this, method);
    }
    if ( this->fields.m_CancellationTokenSource )
    {
      if ( !this_1->fields.cachedFingerStatus )
      {
        this = (AboutUs_o *)this_1->fields.anim;
        this_1->fields.cachedFingerStatus = 1;
        if ( !this )
          goto LABEL_46;
        this = (AboutUs_o *)UnityEngine_Animation__get_Item(
                              (UnityEngine_Animation_o *)this,
                              (System_String_o *)"skip",
                              0);
        if ( !this )
          goto LABEL_46;
        UnityEngine_AnimationState__set_speed((UnityEngine_AnimationState_o *)this, 1.0, 0);
        this = (AboutUs_o *)this_1->fields.anim;
        if ( !this )
          goto LABEL_46;
        UnityEngine_Animation__Play((UnityEngine_Animation_o *)this, 0);
      }
      this = (AboutUs_o *)this_1->fields.anim;
      if ( this )
      {
        this = (AboutUs_o *)UnityEngine_Component__get_gameObject((UnityEngine_Component_o *)this, 0);
        if ( this )
        {
          this = (AboutUs_o *)UnityEngine_GameObject__GetComponent_object_(
                                (UnityEngine_GameObject_o *)this,
                                (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Text___);
          if ( this )
          {
            (*(void (__fastcall **)(AboutUs_o *, _QWORD))&this->klass[1]._2.field_count)(
              this,
              *(_QWORD *)&this->klass[1]._2.interfaces_count);
            if ( v18 <= 0.99 )
              return;
            goto LABEL_39;
          }
        }
      }
      goto LABEL_46;
    }
    if ( this_1->fields.cachedFingerStatus )
    {
      this = (AboutUs_o *)this_1->fields.anim;
      this_1->fields.cachedFingerStatus = 0;
      if ( !this )
        goto LABEL_46;
      this = (AboutUs_o *)UnityEngine_Animation__get_Item(
                            (UnityEngine_Animation_o *)this,
                            (System_String_o *)"skip",
                            0);
      if ( !this )
        goto LABEL_46;
      UnityEngine_AnimationState__set_normalizedTime((UnityEngine_AnimationState_o *)this, 0.33, 0);
      this = (AboutUs_o *)this_1->fields.anim;
      if ( !this )
        goto LABEL_46;
      this = (AboutUs_o *)UnityEngine_Animation__get_Item(
                            (UnityEngine_Animation_o *)this,
                            (System_String_o *)"skip",
                            0);
      if ( !this )
        goto LABEL_46;
      UnityEngine_AnimationState__set_speed((UnityEngine_AnimationState_o *)this, -1.0, 0);
    }
  }
}
