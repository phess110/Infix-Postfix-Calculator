/*
* File: Tree.h
* Creator: Peter Hess
* Created: Oct 10 2017
*/

#include "Tree.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

float evalE(TreeNode*, TreeNode*);
float evalTT(TreeNode*, TreeNode*);
float evalT(TreeNode*, TreeNode*);
float evalFT(TreeNode*, TreeNode*);
float evalF(TreeNode*);
char* evalN(TreeNode*);
char* evalNT(TreeNode*);
char* evalD(TreeNode*);

TreeNode* makeRoot(char* str, int i) {
	TreeNode* rt = (TreeNode*)malloc(sizeof(TreeNode));
	rt->data = str;
	rt->terminal = i;
	rt->leftChild = NULL;
	rt->rightSibling = NULL;
	return rt;
}

TreeNode* makeTree1(char* str, int i, TreeNode* lc) {
	TreeNode* t1 = makeRoot(str, i);
	t1->leftChild = lc;
	return t1;
}

TreeNode* makeTree2(char* str, int i, TreeNode* lc, TreeNode* rc1) {
	TreeNode* t1 = makeRoot(str, i);
	t1->leftChild = lc;
	(t1->leftChild)->rightSibling = rc1;
	return t1;
}

TreeNode* makeTree3(char* str, int i, TreeNode* lc, TreeNode* rc1, TreeNode* rc2) {
	TreeNode* t1 = makeRoot(str, i);
	t1->leftChild = lc;
	lc->rightSibling = rc1;
	rc1->rightSibling = rc2;
	return t1;
}

void freeTreeNode(TreeNode* tn) {
	free(tn->leftChild);
	free(tn->rightSibling);
	free(tn);
}

void freeTree(TreeNode* t) {
	if (t->leftChild) {
		freeTree(t->leftChild);
	}
	else if (t->rightSibling) {
			freeTree(t->rightSibling);
	}
	else if(t){
		freeTreeNode(t);
	}
}

TreeNode* getLeftmostNonTerminal(TreeNode* rt) {
	if (rt->leftChild) {										//There is a lc
		TreeNode* t = getLeftmostNonTerminal(rt->leftChild);		//Search lc tree
		if (t) {
			return t;												//Return lmc in lc tree if it exists
		}
		else {
			if (rt->rightSibling) { return getLeftmostNonTerminal(rt->rightSibling); } //Return lmc in rs if it exists
			/*else if (!(rt->terminal)) {
				return rt;
			}*/
			else { return NULL; }									//Search has failed
		}
	}
	else {															//Roots with no lc 
		if (rt->terminal) {											//Root is terminal
			if (rt->rightSibling) {
				return getLeftmostNonTerminal(rt->rightSibling);	//Return lmc in rs if it exists
			}
			else{ return NULL; }									//No rc, search failed
		}
		else { return rt; }											//Found a lm non-terminal
	}
}

char* evalD(TreeNode* d) {
	return d->leftChild->data; //"0","1",...
}

char* evalNT(TreeNode* nt) {
	char type = (nt->leftChild->data)[0];
	switch (type)
	{
	case 'N':
		return evalN(nt->leftChild);
	default:
		return ""; //epsilon
	}
}

char* concatenate(char* x, char* y) {
	char* cat = (char*)malloc(sizeof(x) + sizeof(y));
	int i = 0;
	for (i; x[i] != '\0'; i++) {
		cat[i] = x[i];
	}
	for (int j = 0; y[j] != '\0'; j++) {
		cat[i] = y[j];
		i++;
	}
	cat[i] = '\0';
	return cat;
}

char* evalN(TreeNode* n) {
	char* s1 = evalD(n->leftChild);
	char* s2 = evalNT(n->leftChild->rightSibling);
	if (strcmp(s2,"") == 0) {
		return s1;
	}
	else{ return concatenate(s1,s2); }
}

float evalF(TreeNode* f) {
	char type = (f->leftChild->data)[0]; //leftchild is either an N or a (
	switch (type)
	{
	case 'N': //Number
		return (float)atof(evalN(f->leftChild)); //converts number str to float
	default: //Parenthesized expression
		return evalTree(f->leftChild->rightSibling); //evaluate expr in parenthesis
	}
}

float evalFT(TreeNode* f, TreeNode* ft) {
	return evalT(f, ft);
}

float evalT(TreeNode* f, TreeNode* ft) {
	char oper = (ft->leftChild->data)[0];
	switch (oper)
	{
	case '*':
		return evalF(f) * evalFT(ft->leftChild->rightSibling, ft->leftChild->rightSibling->rightSibling);
	case '/':
		return evalF(f) / evalFT(ft->leftChild->rightSibling, ft->leftChild->rightSibling->rightSibling);
	default:
		return evalF(f);
	}
}

float evalTT(TreeNode* t, TreeNode* tt) {
	return evalE(t, tt);
}

float evalE(TreeNode* t, TreeNode* tt) {
	char oper = (tt->leftChild->data)[0];
	switch (oper)
	{
	case '+':
		return evalT(t->leftChild, t->leftChild->rightSibling) + evalTT(tt->leftChild->rightSibling, tt->leftChild->rightSibling->rightSibling);
	case '-':
		return evalT(t->leftChild, t->leftChild->rightSibling) - evalTT(tt->leftChild->rightSibling, tt->leftChild->rightSibling->rightSibling);
	default:
		return evalT(t->leftChild, t->leftChild->rightSibling);
	}
}

//Given an expr treenode from a valid parse tree, returns expr's value
float evalTree(TreeNode* e) { 
	return evalE(e->leftChild, e->leftChild->rightSibling); //evalE(<T>,<TT>)
}

void printTreeHelp(TreeNode* rt, int indent) {
	for (int i = 0; i < indent; i++) {
		printf("   "); //Set spacing here
	}
	printf("%s\n", rt->data); //Print root
	if (rt->leftChild != NULL) {
		printTreeHelp(rt->leftChild, indent + 1); //Print left tree
	}
	if (rt->rightSibling != NULL) {
		printTreeHelp(rt->rightSibling, indent); //Print right tree
	}
}

void printTreeNode(TreeNode* rt) {
	if (rt) {
		printf("%s\n", rt->data);
		if (rt->leftChild) { printTreeHelp(rt->leftChild, 1); }
		else { return; }
	}
	else { printf("Parse Failed."); }
}
