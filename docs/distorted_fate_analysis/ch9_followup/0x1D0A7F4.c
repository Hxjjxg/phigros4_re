bool Phigros2_Chapter9_ExoplanetaryMirageUnlock__IsValidUnlockSelection(
        System_Collections_Generic_IReadOnlyList_ChallengeSongItem__o *items,
        const MethodInfo *method)
{
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  System_Collections_Generic_IReadOnlyList_ChallengeSongItem__c *klass; // x8
  __int64 v7; // x9
  int32_t *p_offset; // x10
  __int64 v9; // x0
  System_Collections_Generic_IReadOnlyList_ChallengeSongItem__c *klass_1; // x8
  __int64 v11; // x9
  int32_t *p_offset_1; // x10
  __int64 v13; // x0
  __int64 v14; // x0
  System_Collections_Generic_IReadOnlyList_ChallengeSongItem__c *klass_2; // x8
  __int64 v16; // x20
  __int64 v17; // x9
  int32_t *p_offset_2; // x10
  __int64 v19; // x0
  __int64 v20; // x0
  __int64 v21; // x19
  __int64 v22; // x8
  __int64 v23; // x8
  System_String_o *v24; // x0
  __int64 v25; // x8
  __int64 v26; // x8
  System_String_o *v27; // x0

  if ( (byte_446E6B0 & 1) == 0 ) /*0x1d0a808*/
  {
    sub_1B622A0(&System_Collections_Generic_IReadOnlyCollection_ChallengeSongItem__TypeInfo, method); /*0x1d0a814*/
    sub_1B622A0(&System_Collections_Generic_IReadOnlyList_ChallengeSongItem__TypeInfo, v3); /*0x1d0a820*/
    sub_1B622A0(&"AboutTheUniverse.SOTUIMIssionary.0", v4); /*0x1d0a82c*/
    sub_1B622A0(&"Evanescent.LeaF.0", v5); /*0x1d0a838*/
    byte_446E6B0 = 1; /*0x1d0a840*/
  }
  if ( !items ) /*0x1d0a844*/
    return 0; /*0x1d0a844*/
  klass = items->klass; /*0x1d0a84c*/
  v7 = *(unsigned __int16 *)&items->klass->_2.rank; /*0x1d0a854*/
  if ( *(_WORD *)&items->klass->_2.rank ) /*0x1d0a854*/
  {
    p_offset = &klass->_1.interfaceOffsets->offset; /*0x1d0a864*/
    while ( *((System_Collections_Generic_IReadOnlyCollection_ChallengeSongItem__c **)p_offset - 1) != System_Collections_Generic_IReadOnlyCollection_ChallengeSongItem__TypeInfo ) /*0x1d0a870*/
    {
      --v7; /*0x1d0a874*/
      p_offset += 4; /*0x1d0a878*/
      if ( !v7 ) /*0x1d0a87c*/
        goto LABEL_8; /*0x1d0a87c*/
    }
    v9 = (__int64)&klass->vtable[*p_offset]; /*0x1d0a898*/
  }
  else
  {
LABEL_8:
    v9 = sub_1BC3368(items, System_Collections_Generic_IReadOnlyCollection_ChallengeSongItem__TypeInfo, 0); /*0x1d0a888*/
  }
  if ( (*(unsigned int (__fastcall **)(System_Collections_Generic_IReadOnlyList_ChallengeSongItem__o *, _QWORD))v9)( /*0x1d0a8ac*/
         items,
         *(_QWORD *)(v9 + 8)) != 2 )
    return 0; /*0x1d0a8ac*/
  klass_1 = items->klass; /*0x1d0a8b4*/
  v11 = *(unsigned __int16 *)&items->klass->_2.rank; /*0x1d0a8bc*/
  if ( *(_WORD *)&items->klass->_2.rank ) /*0x1d0a8bc*/
  {
    p_offset_1 = &klass_1->_1.interfaceOffsets->offset; /*0x1d0a8cc*/
    while ( *((System_Collections_Generic_IReadOnlyList_ChallengeSongItem__c **)p_offset_1 - 1) != System_Collections_Generic_IReadOnlyList_ChallengeSongItem__TypeInfo ) /*0x1d0a8d8*/
    {
      --v11; /*0x1d0a8dc*/
      p_offset_1 += 4; /*0x1d0a8e0*/
      if ( !v11 ) /*0x1d0a8e4*/
        goto LABEL_15; /*0x1d0a8e4*/
    }
    v13 = (__int64)&klass_1->vtable[*p_offset_1]; /*0x1d0a900*/
  }
  else
  {
LABEL_15:
    v13 = sub_1BC3368(items, System_Collections_Generic_IReadOnlyList_ChallengeSongItem__TypeInfo, 0); /*0x1d0a8f0*/
  }
  v14 = (*(__int64 (__fastcall **)(System_Collections_Generic_IReadOnlyList_ChallengeSongItem__o *, _QWORD, _QWORD))v13)( /*0x1d0a910*/
          items,
          0,
          *(_QWORD *)(v13 + 8));
  klass_2 = items->klass; /*0x1d0a914*/
  v16 = v14; /*0x1d0a91c*/
  v17 = *(unsigned __int16 *)&items->klass->_2.rank; /*0x1d0a920*/
  if ( *(_WORD *)&items->klass->_2.rank ) /*0x1d0a920*/
  {
    p_offset_2 = &klass_2->_1.interfaceOffsets->offset; /*0x1d0a92c*/
    while ( *((System_Collections_Generic_IReadOnlyList_ChallengeSongItem__c **)p_offset_2 - 1) != System_Collections_Generic_IReadOnlyList_ChallengeSongItem__TypeInfo ) /*0x1d0a938*/
    {
      --v17; /*0x1d0a93c*/
      p_offset_2 += 4; /*0x1d0a940*/
      if ( !v17 ) /*0x1d0a944*/
        goto LABEL_21; /*0x1d0a944*/
    }
    v19 = (__int64)&klass_2->vtable[*p_offset_2]; /*0x1d0a960*/
  }
  else
  {
LABEL_21:
    v19 = sub_1BC3368(items, System_Collections_Generic_IReadOnlyList_ChallengeSongItem__TypeInfo, 0); /*0x1d0a950*/
  }
  v20 = (*(__int64 (__fastcall **)(System_Collections_Generic_IReadOnlyList_ChallengeSongItem__o *, __int64, _QWORD))v19)( /*0x1d0a970*/
          items,
          1,
          *(_QWORD *)(v19 + 8));
  if ( !v16 ) /*0x1d0a974*/
    return 0; /*0x1d0a974*/
  v21 = v20; /*0x1d0a978*/
  if ( !v20 || *(_DWORD *)(v16 + 24) != *(_DWORD *)(v20 + 24) ) /*0x1d0a98c*/
    return 0; /*0x1d0a98c*/
  v22 = *(_QWORD *)(v16 + 16); /*0x1d0a990*/
  if ( v22 && (v23 = *(_QWORD *)(v22 + 16)) != 0 ) /*0x1d0a99c*/
    v24 = *(System_String_o **)(v23 + 16); /*0x1d0a9a0*/
  else
    v24 = 0; /*0x1d0a9a8*/
  if ( !System_String__op_Equality(v24, (System_String_o *)"Evanescent.LeaF.0", 0) ) /*0x1d0a9c0*/
    return 0; /*0x1d0a9e0*/
  v25 = *(_QWORD *)(v21 + 16); /*0x1d0a9c4*/
  if ( v25 && (v26 = *(_QWORD *)(v25 + 16)) != 0 ) /*0x1d0a9d0*/
    v27 = *(System_String_o **)(v26 + 16); /*0x1d0a9d4*/
  else
    v27 = 0; /*0x1d0a9ec*/
  return System_String__op_Equality(v27, (System_String_o *)"AboutTheUniverse.SOTUIMIssionary.0", 0); /*0x1d0a9e8*/
}
