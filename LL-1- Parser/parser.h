/*
* File: parser.h
* Creator: Peter Hess
* Created: Oct 10 2017
*/

#ifndef _PARSER_h
#define _PARSER_h 

#include "Tree.h"
#include "Stack.h"

int parseT[8][17] = {{ 1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,0 },
{ 0,0,0,0,0,0,0,0,0,0,4,2,3,0,0,0,4 },
{ 5,5,5,5,5,5,5,5,5,5,0,0,0,0,0,5,0 },
{ 0,0,0,0,0,0,0,0,0,0,8,8,8,6,7,0,8 },
{ 9,9,9,9,9,9,9,9,9,9,0,0,0,0,0,10,0 },
{ 11,11,11,11,11,11,11,11,11,11,0,0,0,0,0,0,0 },
{ 12,12,12,12,12,12,12,12,12,12,13,13,13,13,13,0,13 },
{ 14,15,16,17,18,19,20,21,22,23,0,0,0,0,0,0,0 }};

//Parsing functions for arithmetic expressions
extern TreeNode* E();
extern TreeNode* T();
extern TreeNode* TT();
extern TreeNode* F();
extern TreeNode* FT();
extern TreeNode* N();
extern TreeNode* NT();
extern TreeNode* D();
#endif