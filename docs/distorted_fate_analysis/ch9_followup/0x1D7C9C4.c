void LevelOverControl__HandleC9EvanescentUnlock(LevelOverControl_o *this, const MethodInfo *method)
{
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  bool hasReadDecrypt; // w20
  float v7; // s0
  float v8; // s1
  float v9; // s2
  _BOOL8 Bool; // x0
  __int64 v11; // x1
  struct LevelStartInfo_o *levelStartInfo; // x8
  int *Bool_1; // x19
  float v14; // s0
  float v15; // s1
  float v16; // s2

  if ( (byte_446EA2B & 1) == 0 ) /*0x1d7c9ec*/
  {
    sub_1B622A0(&SaveManagement_TypeInfo, method); /*0x1d7c9f8*/
    sub_1B622A0(&"C9EvanescentUnlocked", v3); /*0x1d7ca04*/
    sub_1B622A0(&"Evanescent.LeaF.0", v4); /*0x1d7ca10*/
    sub_1B622A0(&"poyidomejiu0", v5); /*0x1d7ca1c*/
    byte_446EA2B = 1; /*0x1d7ca24*/
  }
  hasReadDecrypt = Phigros2_Chapter9_Key__IsCollectionRead((System_String_o *)"poyidomejiu0", 0); /*0x1d7ca40*/
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d7ca44*/
    j_il2cpp_runtime_class_init_0(v7, v8, v9); /*0x1d7ca50*/
  Bool = SaveManagement__LoadBool((System_String_o *)"C9EvanescentUnlocked", 0, 0); /*0x1d7ca60*/
  levelStartInfo = this->fields._levelStartInfo; /*0x1d7ca64*/
  if ( !levelStartInfo ) /*0x1d7ca68*/
    sub_1B624F8(Bool, v11); /*0x1d7cae0*/
  Bool_1 = Bool; /*0x1d7ca6c*/
  if ( EvanescentUnlockEvent__ShouldUnlockAfterCompletion(levelStartInfo->fields.songsId, hasReadDecrypt, Bool, 0) ) /*0x1d7ca80*/
  {
    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d7ca8c*/
      j_il2cpp_runtime_class_init_0(v14, v15, v16); /*0x1d7ca94*/
    SaveManagement__SaveBool((System_String_o *)"C9EvanescentUnlocked", 1, 0); /*0x1d7caa4*/
    Phigros2_Chapter9_Chapter9UnlockNavigation__ApplyReturnToSelectMusicIfNeeded( /*0x1d7cacc*/
      (System_String_o *)"Evanescent.LeaF.0",
      Bool_1,
      1,
      0);
  }
}
