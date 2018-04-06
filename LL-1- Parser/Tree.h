/*
* File: Tree.h
* Creator: Peter Hess
* Created: Oct 10 2017
*/

#ifndef _TREE_h
#define _TREE_h

#include "Tree.h"
#include <stdlib.h>
#include <stdio.h>

//Tree data structure: Leftmost-child/Right-sibling
typedef struct TNode{
	char* data;
	int terminal;   //1 if terminal, 0 if non-terminal
	struct TNode *leftChild;
	struct TNode *rightSibling;
}TreeNode;

extern TreeNode* makeRoot(char*, int);				//Make a treenode with storing a string and an int
extern TreeNode* makeTree1(char*, int, TreeNode*);	//Make a tree with one child
extern TreeNode* makeTree2(char*, int, TreeNode*, TreeNode*); //Make a tree with two children
extern TreeNode* makeTree3(char*, int, TreeNode*, TreeNode*, TreeNode*); //Make a tree with three children

extern void freeTreeNode(TreeNode*);				//Tree destructor
extern TreeNode* getLeftmostNonTerminal(TreeNode*);	//Returns leftmost non-terminal if it exists
extern float evalTree(TreeNode*);					//Evaluate the given parse tree
extern void printTreeNode(TreeNode* rt);			//Print tree function
#endif