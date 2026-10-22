bool DFUnlockControl__UnlockAnimation_d__16__MoveNext(
        DFUnlockControl__UnlockAnimation_d__16_o *this,
        const MethodInfo *method)
{
  __int64 v2; // x2
  DFUnlockControl__UnlockAnimation_d__16_o *this_1; // x19
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
  struct DFUnlockControl_o *_4__this; // x20
  System_Func_bool__o *predicate; // x21
  UnityEngine_WaitUntil_o *__2__current; // x20
  bool v18; // w21
  System_Func_bool__o *predicate_1; // x21
  UnityEngine_WaitUntil_o *__2__current_1; // x20
  int n2; // w8
  System_Func_bool__o *predicate_2; // x21
  UnityEngine_WaitUntil_o *__2__current_2; // x20
  UnityEngine_WaitForSeconds_o *__2__current_3; // x20
  System_Func_bool__o *predicate_3; // x22
  UnityEngine_WaitUntil_o *__2__current_4; // x20
  UnityEngine_Object_o *gameObject; // x21
  float v28; // s0
  float v29; // s1
  float v30; // s2
  System_Func_bool__o *predicate_4; // x21
  UnityEngine_WaitUntil_o *__2__current_5; // x20
  const MethodInfo *method_1; // x1
  System_Collections_IEnumerator_o *routine; // x0
  System_Func_bool__o *predicate_5; // x21
  UnityEngine_WaitUntil_o *__2__current_6; // x20
  System_Func_bool__o *predicate_6; // x21
  UnityEngine_WaitUntil_o *__2__current_7; // x20

  this_1 = this;
  if ( (byte_446E436 & 1) == 0 )
  {
    sub_1B622A0(&Method_DFUnlockControl__UnlockAnimation_b__16_0__, method);
    sub_1B622A0(&Method_DFUnlockControl__UnlockAnimation_b__16_1__, v4);
    sub_1B622A0(&Method_DFUnlockControl__UnlockAnimation_b__16_2__, v5);
    sub_1B622A0(&Method_DFUnlockControl__UnlockAnimation_b__16_3__, v6);
    sub_1B622A0(&Method_DFUnlockControl__UnlockAnimation_b__16_4__, v7);
    sub_1B622A0(&Method_DFUnlockControl__UnlockAnimation_b__16_5__, v8);
    sub_1B622A0(&Method_DFUnlockControl__UnlockAnimation_b__16_6__, v9);
    sub_1B622A0(&System_Func_bool__TypeInfo, v10);
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v11);
    sub_1B622A0(&UnityEngine_WaitForSeconds_TypeInfo, v12);
    sub_1B622A0(&UnityEngine_WaitUntil_TypeInfo, v13);
    this = (DFUnlockControl__UnlockAnimation_d__16_o *)sub_1B622A0(&"Level", v14);
    byte_446E436 = 1;
  }
  _4__this = this_1->fields.__4__this;
  switch ( this_1->fields.__1__state )
  {
    case 0:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_29;
      this = (DFUnlockControl__UnlockAnimation_d__16_o *)_4__this->fields.touch2Start;
      if ( !this )
        goto LABEL_29;
      UnityEngine_Video_VideoPlayer__set_time((UnityEngine_Video_VideoPlayer_o *)this, 2.066, 0);
      this = (DFUnlockControl__UnlockAnimation_d__16_o *)_4__this->fields.touch2Start;
      if ( !this )
        goto LABEL_29;
      UnityEngine_Video_VideoPlayer__Play((UnityEngine_Video_VideoPlayer_o *)this, 0);
      predicate = (System_Func_bool__o *)sub_1B624E8(System_Func_bool__TypeInfo);
      System_Func_bool____ctor(
        predicate,
        (Il2CppObject *)_4__this,
        Method_DFUnlockControl__UnlockAnimation_b__16_0__,
        0);
      __2__current = (UnityEngine_WaitUntil_o *)sub_1B624E8(UnityEngine_WaitUntil_TypeInfo);
      UnityEngine_WaitUntil___ctor(__2__current, predicate, 0);
      v18 = 1;
      this_1->fields.__2__current = (Il2CppObject *)__2__current;
      this_1->fields.__1__state = 1;
      return v18;
    case 1:
      this_1->fields.__1__state = -1;
      predicate_1 = (System_Func_bool__o *)sub_1B624E8(System_Func_bool__TypeInfo);
      System_Func_bool____ctor(
        predicate_1,
        (Il2CppObject *)_4__this,
        Method_DFUnlockControl__UnlockAnimation_b__16_1__,
        0);
      __2__current_1 = (UnityEngine_WaitUntil_o *)sub_1B624E8(UnityEngine_WaitUntil_TypeInfo);
      UnityEngine_WaitUntil___ctor(__2__current_1, predicate_1, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_1;
      n2 = 2;
      goto LABEL_25;
    case 2:
      this_1->fields.__1__state = -1;
      predicate_2 = (System_Func_bool__o *)sub_1B624E8(System_Func_bool__TypeInfo);
      System_Func_bool____ctor(
        predicate_2,
        (Il2CppObject *)_4__this,
        Method_DFUnlockControl__UnlockAnimation_b__16_2__,
        0);
      __2__current_2 = (UnityEngine_WaitUntil_o *)sub_1B624E8(UnityEngine_WaitUntil_TypeInfo);
      UnityEngine_WaitUntil___ctor(__2__current_2, predicate_2, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_2;
      n2 = 3;
      goto LABEL_25;
    case 3:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_29;
      _4__this->fields._isPlaying = 0;
      __2__current_3 = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo);
      UnityEngine_WaitForSeconds___ctor(__2__current_3, 0.2, 0);
      n2 = 4;
      this_1->fields.__2__current = (Il2CppObject *)__2__current_3;
      goto LABEL_25;
    case 4:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_29;
      this = (DFUnlockControl__UnlockAnimation_d__16_o *)_4__this->fields.blackMask;
      if ( !this )
        goto LABEL_29;
      v18 = 1;
      UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)this, 1, 0);
      predicate_3 = (System_Func_bool__o *)sub_1B624E8(System_Func_bool__TypeInfo);
      System_Func_bool____ctor(
        predicate_3,
        (Il2CppObject *)_4__this,
        Method_DFUnlockControl__UnlockAnimation_b__16_3__,
        0);
      __2__current_4 = (UnityEngine_WaitUntil_o *)sub_1B624E8(UnityEngine_WaitUntil_TypeInfo);
      UnityEngine_WaitUntil___ctor(__2__current_4, predicate_3, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_4;
      this_1->fields.__1__state = 5;
      return v18;
    case 5:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_29;
      this = (DFUnlockControl__UnlockAnimation_d__16_o *)_4__this->fields.player;
      if ( !this )
        goto LABEL_29;
      UnityEngine_Video_VideoPlayer__Play((UnityEngine_Video_VideoPlayer_o *)this, 0);
      this = (DFUnlockControl__UnlockAnimation_d__16_o *)_4__this->fields.touch2Start;
      if ( !this )
        goto LABEL_29;
      gameObject = (UnityEngine_Object_o *)UnityEngine_Component__get_gameObject((UnityEngine_Component_o *)this, 0);
      if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v28, v29, v30);
      UnityEngine_Object__Destroy_63300388(gameObject, 0);
      predicate_4 = (System_Func_bool__o *)sub_1B624E8(System_Func_bool__TypeInfo);
      System_Func_bool____ctor(
        predicate_4,
        (Il2CppObject *)_4__this,
        Method_DFUnlockControl__UnlockAnimation_b__16_4__,
        0);
      __2__current_5 = (UnityEngine_WaitUntil_o *)sub_1B624E8(UnityEngine_WaitUntil_TypeInfo);
      UnityEngine_WaitUntil___ctor(__2__current_5, predicate_4, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_5;
      n2 = 6;
      goto LABEL_25;
    case 6:
      this_1->fields.__1__state = -1;
      if ( !_4__this || (this = (DFUnlockControl__UnlockAnimation_d__16_o *)_4__this->fields.blackMask) == 0 )
LABEL_29:
        sub_1B624F8(this, method, v2);
      UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)this, 0, 0);
      routine = DFUnlockControl__MusicInfoAnimation(_4__this, method_1);
      UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)_4__this, routine, 0);
      predicate_5 = (System_Func_bool__o *)sub_1B624E8(System_Func_bool__TypeInfo);
      System_Func_bool____ctor(
        predicate_5,
        (Il2CppObject *)_4__this,
        Method_DFUnlockControl__UnlockAnimation_b__16_5__,
        0);
      __2__current_6 = (UnityEngine_WaitUntil_o *)sub_1B624E8(UnityEngine_WaitUntil_TypeInfo);
      UnityEngine_WaitUntil___ctor(__2__current_6, predicate_5, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_6;
      n2 = 7;
      goto LABEL_25;
    case 7:
      this_1->fields.__1__state = -1;
      predicate_6 = (System_Func_bool__o *)sub_1B624E8(System_Func_bool__TypeInfo);
      System_Func_bool____ctor(
        predicate_6,
        (Il2CppObject *)_4__this,
        Method_DFUnlockControl__UnlockAnimation_b__16_6__,
        0);
      __2__current_7 = (UnityEngine_WaitUntil_o *)sub_1B624E8(UnityEngine_WaitUntil_TypeInfo);
      UnityEngine_WaitUntil___ctor(__2__current_7, predicate_6, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_7;
      n2 = 8;
LABEL_25:
      this_1->fields.__1__state = n2;
      return 1;
    case 8:
      this_1->fields.__1__state = -1;
      BlackScreen__Show((System_String_o *)"Level", 2.0, 0);
      return 0;
    default:
      return 0;
  }
}
