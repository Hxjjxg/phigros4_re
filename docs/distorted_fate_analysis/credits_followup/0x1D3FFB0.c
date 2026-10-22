bool C9AfterDeductionControl__Start_d__12__MoveNext(
        C9AfterDeductionControl__Start_d__12_o *this,
        const MethodInfo *method)
{
  float v2; // s0
  float v3; // s1
  float v4; // s2
  C9AfterDeductionControl__Start_d__12_o *this_1; // x19
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
  struct C9AfterDeductionControl_o *_4__this; // x21
  struct GameInformation_o *main; // x8
  __int64 v18; // x0
  __int64 v19; // x1
  UnityEngine_Video_VideoPlayer_o *videoPlayer; // x0
  UnityEngine_WaitForSeconds_o *__2__current; // x20
  UnityEngine_Video_VideoPlayer_o *videoPlayer_2; // x0
  float time; // s8
  float i_5__3; // s8
  float deltaTime; // s0
  int n2; // w8
  UnityEngine_Video_VideoPlayer_o *videoPlayer_1; // x0
  UnityEngine_WaitForSeconds_o *__2__current_1; // x20
  float videoContentLength; // s9
  UnityEngine_WaitForSeconds_o *__2__current_2; // x20
  UnityEngine_Color_o value_1; // 0:s0.4,4:s1.4,8:s2.4,12:s3.4
  UnityEngine_Color_o value_2; // 0:s0.4,4:s1.4,8:s2.4,12:s3.4
  UnityEngine_Color_o value; // 0:s0.4,4:s1.4,8:s2.4,12:s3.4

  this_1 = this; /*0x1d3ffc8*/
  if ( (byte_446E89D & 1) == 0 ) /*0x1d3ffcc*/
  {
    sub_1B622A0(&SaveManagement_TypeInfo, method); /*0x1d3ffd8*/
    sub_1B622A0(&UnityEngine_WaitForSeconds_TypeInfo, v6); /*0x1d3ffe4*/
    sub_1B622A0(&"4.0", v7); /*0x1d3fff0*/
    sub_1B622A0(&"GameCompleted", v8); /*0x1d3fffc*/
    sub_1B622A0(&"C9PendingMessageAfterQuit", v9); /*0x1d40008*/
    sub_1B622A0(&"C9PendingMessageLevel", v10); /*0x1d40014*/
    sub_1B622A0(&"C9HateUnlocked", v11); /*0x1d40020*/
    sub_1B622A0(&"C9DesultorySignalsUnlocked", v12); /*0x1d4002c*/
    sub_1B622A0(&"C9MessageUnlocked", v13); /*0x1d40038*/
    sub_1B622A0(&"Chapter9Phase2Passed", v14); /*0x1d40044*/
    this = (C9AfterDeductionControl__Start_d__12_o *)sub_1B622A0(&"C9HappyEndingUnlocked", v15); /*0x1d40050*/
    byte_446E89D = 1; /*0x1d40058*/
  }
  _4__this = this_1->fields.__4__this; /*0x1d40068*/
  switch ( this_1->fields.__1__state ) /*0x1d40080*/
  {
    case 0: /*0x1d40080*/
      this_1->fields.__1__state = -1; /*0x1d40088*/
      if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d40098*/
        j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1d400a0*/
      SaveManagement__SaveBool((System_String_o *)"Chapter9Phase2Passed", 1, 0); /*0x1d400bc*/
      SaveManagement__SaveBool((System_String_o *)"C9HateUnlocked", 1, 0); /*0x1d400d4*/
      SaveManagement__SaveBool((System_String_o *)"C9DesultorySignalsUnlocked", 1, 0); /*0x1d400ec*/
      SaveManagement__SaveBool((System_String_o *)"C9MessageUnlocked", 1, 0); /*0x1d40104*/
      SaveManagement__SaveBool((System_String_o *)"C9HappyEndingUnlocked", 1, 0); /*0x1d4011c*/
      SaveManagement__DeleteKey((System_String_o *)"C9PendingMessageAfterQuit", 0); /*0x1d40130*/
      SaveManagement__DeleteKey((System_String_o *)"C9PendingMessageLevel", 0); /*0x1d40144*/
      SaveManagement__SaveString((System_String_o *)"GameCompleted", (System_String_o *)"4.0", 0); /*0x1d40164*/
      if ( !byte_446E372 ) /*0x1d40170*/
      {
        this = (C9AfterDeductionControl__Start_d__12_o *)sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d4017c*/
        byte_446E372 = 1; /*0x1d40180*/
      }
      main = GameInformation_TypeInfo->static_fields->_main; /*0x1d40194*/
      if ( !main ) /*0x1d40198*/
        goto LABEL_39; /*0x1d40198*/
      this = (C9AfterDeductionControl__Start_d__12_o *)main->fields.getCollectionControl; /*0x1d4019c*/
      if ( !this ) /*0x1d401a0*/
        goto LABEL_39; /*0x1d401a0*/
      GetCollectionControl__EvaluateStateUnlocks((GetCollectionControl_o *)this, 0); /*0x1d401a8*/
      UnityEngine_PlayerPrefs__Save(0); /*0x1d401b0*/
      if ( !_4__this ) /*0x1d401b4*/
        sub_1B624F8(v18, v19); /*0x1d403b4*/
      videoPlayer = _4__this->fields.videoPlayer; /*0x1d401b8*/
      if ( !videoPlayer ) /*0x1d401bc*/
        sub_1B624F8(0, v19); /*0x1d403b8*/
      UnityEngine_Video_VideoPlayer__Prepare(videoPlayer, 0); /*0x1d401c4*/
      __2__current = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo); /*0x1d401e0*/
      UnityEngine_WaitForSeconds___ctor(__2__current, 1.0, 0); /*0x1d401e4*/
      this_1->fields.__2__current = (Il2CppObject *)__2__current; /*0x1d401ec*/
      this_1->fields.__1__state = 1; /*0x1d401f0*/
      return 1; /*0x1d401f4*/
    case 1: /*0x1d40080*/
      this_1->fields.__1__state = -1; /*0x1d402c4*/
      if ( !_4__this ) /*0x1d402c8*/
        sub_1B624F8(this, method); /*0x1d403a8*/
      videoPlayer_1 = _4__this->fields.videoPlayer; /*0x1d402cc*/
      if ( !videoPlayer_1 ) /*0x1d402d0*/
        sub_1B624F8(0, method); /*0x1d403b0*/
      UnityEngine_Video_VideoPlayer__Play(videoPlayer_1, 0); /*0x1d402d8*/
      __2__current_1 = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo); /*0x1d402f4*/
      UnityEngine_WaitForSeconds___ctor(__2__current_1, 10.0, 0); /*0x1d402f8*/
      this_1->fields.__2__current = (Il2CppObject *)__2__current_1; /*0x1d402fc*/
      n2 = 2; /*0x1d40300*/
      goto LABEL_32; /*0x1d40304*/
    case 2: /*0x1d40080*/
      this_1->fields.__1__state = -1; /*0x1d4020c*/
      if ( !_4__this ) /*0x1d40210*/
        sub_1B624F8(this, method); /*0x1d403a4*/
      videoPlayer_2 = _4__this->fields.videoPlayer; /*0x1d40214*/
      if ( !videoPlayer_2 ) /*0x1d40218*/
        sub_1B624F8(0, method); /*0x1d403ac*/
      time = UnityEngine_Video_VideoPlayer__get_time(videoPlayer_2, 0); /*0x1d40224*/
      this = (C9AfterDeductionControl__Start_d__12_o *)_4__this->fields.endObject; /*0x1d40228*/
      if ( !this ) /*0x1d4022c*/
        goto LABEL_39; /*0x1d4022c*/
      _4__this->fields.endPosition = (struct UnityEngine_Vector2_o)vsub_f32( /*0x1d40248*/
                                                                     (float32x2_t)_4__this->fields.startPosition,
                                                                     (float32x2_t)UnityEngine_Transform__get_position(
                                                                                    (UnityEngine_Transform_o *)this,
                                                                                    0)).n64_u64[0];
      this = (C9AfterDeductionControl__Start_d__12_o *)UnityEngine_Camera__get_main(0); /*0x1d4024c*/
      this_1->fields._camera_5__2 = (struct UnityEngine_Camera_o *)this; /*0x1d40258*/
      if ( time >= 0.1 ) /*0x1d40260*/
      {
        if ( this ) /*0x1d40340*/
        {
          value.fields.r = 0.0; /*0x1d40344*/
          value.fields.g = 0.0; /*0x1d40348*/
          value.fields.b = 0.0; /*0x1d4034c*/
          value.fields.a = 1.0; /*0x1d40350*/
          UnityEngine_Camera__set_backgroundColor((UnityEngine_Camera_o *)this, value, 0); /*0x1d40358*/
          videoContentLength = _4__this->fields.videoContentLength; /*0x1d40360*/
          __2__current_2 = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo); /*0x1d40378*/
          UnityEngine_WaitForSeconds___ctor(__2__current_2, videoContentLength - time, 0); /*0x1d4037c*/
          this_1->fields.__2__current = (Il2CppObject *)__2__current_2; /*0x1d40380*/
          n2 = 4; /*0x1d40384*/
          goto LABEL_32; /*0x1d40384*/
        }
LABEL_39:
        sub_1B624F8(this, method); /*0x1d4040c*/
      }
      value_1.fields.r = 1.0; /*0x1d40268*/
      this_1->fields._i_5__3 = 1.0; /*0x1d4026c*/
LABEL_21:
      if ( !this ) /*0x1d4029c*/
        goto LABEL_39; /*0x1d4029c*/
      value_1.fields.a = 1.0; /*0x1d402a0*/
      value_1.fields.g = value_1.fields.r; /*0x1d402a4*/
      value_1.fields.b = value_1.fields.r; /*0x1d402a8*/
      UnityEngine_Camera__set_backgroundColor((UnityEngine_Camera_o *)this, value_1, 0); /*0x1d402b0*/
      n2 = 3; /*0x1d402b4*/
      this_1->fields.__2__current = 0; /*0x1d402b8*/
LABEL_32:
      this_1->fields.__1__state = n2; /*0x1d40388*/
      return 1;
    case 3: /*0x1d40080*/
      i_5__3 = this_1->fields._i_5__3; /*0x1d40274*/
      this_1->fields.__1__state = -1; /*0x1d40280*/
      deltaTime = UnityEngine_Time__get_deltaTime(0); /*0x1d40284*/
      this = (C9AfterDeductionControl__Start_d__12_o *)this_1->fields._camera_5__2; /*0x1d40288*/
      value_1.fields.r = i_5__3 - deltaTime; /*0x1d4028c*/
      this_1->fields._i_5__3 = value_1.fields.r; /*0x1d40294*/
      if ( value_1.fields.r > 0.0 ) /*0x1d40298*/
        goto LABEL_21; /*0x1d40298*/
      if ( !this ) /*0x1d40310*/
        goto LABEL_39; /*0x1d40310*/
      value_2.fields.r = 0.0; /*0x1d40314*/
      value_2.fields.g = 0.0; /*0x1d40318*/
      value_2.fields.b = 0.0; /*0x1d4031c*/
      value_2.fields.a = 1.0; /*0x1d40320*/
      UnityEngine_Camera__set_backgroundColor((UnityEngine_Camera_o *)this, value_2, 0); /*0x1d40328*/
      if ( !_4__this ) /*0x1d4032c*/
        goto LABEL_39; /*0x1d4032c*/
LABEL_29:
      _4__this->fields.playing = 1; /*0x1d40330*/
      return 0; /*0x1d4033c*/
    case 4: /*0x1d40080*/
      this_1->fields.__1__state = -1; /*0x1d401fc*/
      if ( !_4__this ) /*0x1d40200*/
        goto LABEL_39; /*0x1d40200*/
      goto LABEL_29; /*0x1d40200*/
    default:
      return 0; /*0x1d4030c*/
  }
}
