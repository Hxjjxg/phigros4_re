void LevelOverControl__HandleC9ImplexroughUnlock(LevelOverControl_o *this, const MethodInfo *method)
{
  LevelOverControl_o *this_1; // x19
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  struct LevelStartInfo_o *levelStartInfo; // x8
  float v7; // s0
  float v8; // s1
  float v9; // s2
  float v10; // s0
  float v11; // s1
  float v12; // s2

  this_1 = this; /*0x1d7cdd4*/
  if ( (byte_446EA2D & 1) == 0 ) /*0x1d7cdd8*/
  {
    sub_1B622A0(&SaveManagement_TypeInfo, method); /*0x1d7cde4*/
    sub_1B622A0(&"poyidomegino0", v3); /*0x1d7cdf0*/
    sub_1B622A0(&"Implexrough.Silentroommommy.0", v4); /*0x1d7cdfc*/
    this = (LevelOverControl_o *)sub_1B622A0(&"C9ImplexroughUnlocked", v5); /*0x1d7ce08*/
    byte_446EA2D = 1; /*0x1d7ce10*/
  }
  levelStartInfo = this_1->fields._levelStartInfo; /*0x1d7ce14*/
  if ( !levelStartInfo ) /*0x1d7ce18*/
    sub_1B624F8(this, method); /*0x1d7cecc*/
  if ( !System_String__op_Inequality(levelStartInfo->fields.songsId, (System_String_o *)"Implexrough.Silentroommommy.0", 0) /*0x1d7ce48*/
    && Phigros2_Chapter9_Key__IsCollectionRead((System_String_o *)"poyidomegino0", 0) )
  {
    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d7ce5c*/
      j_il2cpp_runtime_class_init_0(v7, v8, v9); /*0x1d7ce64*/
    if ( !SaveManagement__LoadBool((System_String_o *)"C9ImplexroughUnlocked", 0, 0) ) /*0x1d7ce80*/
    {
      if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d7ce94*/
        j_il2cpp_runtime_class_init_0(v10, v11, v12); /*0x1d7ce9c*/
      SaveManagement__SaveBool((System_String_o *)"C9ImplexroughUnlocked", 1, 0); /*0x1d7ceac*/
      Phigros2_Chapter9_Chapter9UnlockNavigation__ApplyReturnToSelectMusicIfNeeded( /*0x1d7cec8*/
        (System_String_o *)"Implexrough.Silentroommommy.0",
        0,
        1,
        0);
    }
  }
}
