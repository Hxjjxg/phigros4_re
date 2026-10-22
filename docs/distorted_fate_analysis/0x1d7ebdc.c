void LevelOverControl___c__DisplayClass43_0___Start_g__Handle92wRequirement_6(
        LevelOverControl___c__DisplayClass43_0_o *this,
        const MethodInfo *method)
{
  __int64 v2; // x2
  LevelOverControl___c__DisplayClass43_0_o *this_1; // x19
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
  __int64 v23; // x1
  __int64 v24; // x1
  struct LevelOverControl_o *_4__this; // x8
  struct LevelResultInfo_o *levelResultInfo; // x9
  struct LevelStartInfo_o *levelStartInfo; // x8
  struct LevelOverControl_o *__4__this; // x8
  struct LevelStartInfo_o *_levelStartInfo; // x8
  System_String_o *songsId; // x19
  uint32_t n1608274664; // w0
  float v32; // s0
  float v33; // s1
  float v34; // s2
  __int64 *p_StringLiteral_3685; // x8
  float v36; // s0
  float v37; // s1
  float v38; // s2
  float v39; // s0
  float v40; // s1
  float v41; // s2
  float v42; // s0
  float v43; // s1
  float v44; // s2
  float v45; // s0
  float v46; // s1
  float v47; // s2
  float v48; // s0
  float v49; // s1
  float v50; // s2
  float v51; // s0
  float v52; // s1
  float v53; // s2
  float v54; // s0
  float v55; // s1
  float v56; // s2
  float v57; // s0
  float v58; // s1
  float v59; // s2
  float v60; // s0
  float v61; // s1
  float v62; // s2

  this_1 = this;
  if ( (byte_446EA46 & 1) == 0 )
  {
    sub_1B622A0(&SaveManagement_TypeInfo, method);
    sub_1B622A0(&"inferiorINSGrade", v4);
    sub_1B622A0(&"DistortedFateINSGrade", v5);
    sub_1B622A0(&"DistortedFate.Sakuzyo.0", v6);
    sub_1B622A0(&"YouaretheMiserableINSGrade", v7);
    sub_1B622A0(&"iLArtifactINSGrade", v8);
    sub_1B622A0(&"ShadowINSGrade", v9);
    sub_1B622A0(&"IN", v10);
    sub_1B622A0(&"YouaretheMiserable.tpazolite.0", v11);
    sub_1B622A0(&"心之所向.姜米條.0", v12);
    sub_1B622A0(&"Cuvism.Fl00tvsHalv.0", v13);
    sub_1B622A0(&"心之所向INSGrade", v14);
    sub_1B622A0(&"inferior.姜米條.0", v15);
    sub_1B622A0(&"StasisINSGrade", v16);
    sub_1B622A0(&"CuvismINSGrade", v17);
    sub_1B622A0(&"atruthseekerCommunicationwithUtopiawillbelost.kuro.0", v18);
    sub_1B622A0(&"DESTRUCTION321.Normal1zervsBrokenNerdz.0", v19);
    sub_1B622A0(&"iLArtifact.Link0.0", v20);
    sub_1B622A0(&"DESTRUCTION321INSGrade", v21);
    sub_1B622A0(&"Stasis.Maozon.0", v22);
    sub_1B622A0(&"atruthseekerCommunicationwithUtopiawillbelostINSGrade", v23);
    this = (LevelOverControl___c__DisplayClass43_0_o *)sub_1B622A0(&"Shadow.SumaiLightvs姜米條.0", v24);
    byte_446EA46 = 1;
  }
  _4__this = this_1->fields.__4__this;
  if ( !_4__this )
    goto LABEL_64;
  levelResultInfo = _4__this->fields.levelResultInfo;
  if ( !levelResultInfo )
    goto LABEL_64;
  if ( levelResultInfo->fields._IntScore_k__BackingField < 920000 )
    return;
  levelStartInfo = _4__this->fields._levelStartInfo;
  if ( !levelStartInfo )
    goto LABEL_64;
  this = (LevelOverControl___c__DisplayClass43_0_o *)System_String__op_Inequality(
                                                       levelStartInfo->fields.songsLevel,
                                                       (System_String_o *)"IN",
                                                       0);
  if ( ((unsigned __int8)this & 1) != 0 )
    return;
  __4__this = this_1->fields.__4__this;
  if ( !__4__this || (_levelStartInfo = __4__this->fields._levelStartInfo) == 0 )
LABEL_64:
    sub_1B624F8(this, method, v2);
  songsId = _levelStartInfo->fields.songsId;
  n1608274664 = PrivateImplementationDetails___ComputeStringHash_30585180(songsId, 0);
  if ( n1608274664 > 0x75715453 )
  {
    if ( n1608274664 > 0xBE5465F5 )
    {
      if ( n1608274664 == -412029367 )
      {
        if ( !System_String__op_Equality(songsId, (System_String_o *)"DESTRUCTION321.Normal1zervsBrokenNerdz.0", 0) )
          return;
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v57, v58, v59);
        p_StringLiteral_3685 = &"DESTRUCTION321INSGrade";
        goto LABEL_63;
      }
      if ( n1608274664 == -235129039 )
      {
        if ( !System_String__op_Equality(songsId, (System_String_o *)"Shadow.SumaiLightvs姜米條.0", 0) )
          return;
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v60, v61, v62);
        p_StringLiteral_3685 = &"ShadowINSGrade";
        goto LABEL_63;
      }
      if ( n1608274664 == -69576108 && System_String__op_Equality(songsId, (System_String_o *)"心之所向.姜米條.0", 0) )
      {
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v42, v43, v44);
        p_StringLiteral_3685 = &"心之所向INSGrade";
        goto LABEL_63;
      }
    }
    else
    {
      if ( n1608274664 == -1435344454 )
      {
        if ( !System_String__op_Equality(songsId, (System_String_o *)"iLArtifact.Link0.0", 0) )
          return;
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v48, v49, v50);
        p_StringLiteral_3685 = &"iLArtifactINSGrade";
        goto LABEL_63;
      }
      if ( n1608274664 == -1101765131 && System_String__op_Equality(songsId, (System_String_o *)"DistortedFate.Sakuzyo.0", 0) )
      {
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v36, v37, v38);
        p_StringLiteral_3685 = &"DistortedFateINSGrade";
        goto LABEL_63;
      }
    }
  }
  else if ( n1608274664 > 0x470CED88 )
  {
    if ( n1608274664 == 1608274664 )
    {
      if ( !System_String__op_Equality(songsId, (System_String_o *)"Cuvism.Fl00tvsHalv.0", 0) )
        return;
      if ( !SaveManagement_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v51, v52, v53);
      p_StringLiteral_3685 = &"CuvismINSGrade";
      goto LABEL_63;
    }
    if ( n1608274664 == 1661339811 )
    {
      if ( !System_String__op_Equality(songsId, (System_String_o *)"YouaretheMiserable.tpazolite.0", 0) )
        return;
      if ( !SaveManagement_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v54, v55, v56);
      p_StringLiteral_3685 = &"YouaretheMiserableINSGrade";
      goto LABEL_63;
    }
    if ( n1608274664 == 1970361427 && System_String__op_Equality(songsId, (System_String_o *)"inferior.姜米條.0", 0) )
    {
      if ( !SaveManagement_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v39, v40, v41);
      p_StringLiteral_3685 = &"inferiorINSGrade";
      goto LABEL_63;
    }
  }
  else
  {
    if ( n1608274664 == 654398168 )
    {
      if ( !System_String__op_Equality(songsId, (System_String_o *)"Stasis.Maozon.0", 0) )
        return;
      if ( !SaveManagement_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v45, v46, v47);
      p_StringLiteral_3685 = &"StasisINSGrade";
      goto LABEL_63;
    }
    if ( n1608274664 == 1192029576 && System_String__op_Equality(songsId, (System_String_o *)"atruthseekerCommunicationwithUtopiawillbelost.kuro.0", 0) )
    {
      if ( !SaveManagement_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v32, v33, v34);
      p_StringLiteral_3685 = &"atruthseekerCommunicationwithUtopiawillbelostINSGrade";
LABEL_63:
      SaveManagement__SaveBool((System_String_o *)*p_StringLiteral_3685, 1, 0);
    }
  }
}
