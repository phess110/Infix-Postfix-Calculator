/*
* File: parser.c
* Creator: Peter Hess
* Created: Oct 10 2017
*/
//#pragma warning(disable:4996)
//#pragma warning(disable:4244)
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "parser.h"
#include "Stack.h"
#include "Tree.h"

TreeNode* parseTreeNode;
TreeNode* parseTreeNode2;
char* nextTerminal;
TreeNode* N();
TreeNode* NT();
TreeNode* D();

int parseTable(int sc, int la) {
	return parseT[sc][la];
}

TreeNode* D() {
	if (*nextTerminal > 47 && *nextTerminal < 58) { //Digit 0-9
		char* str[10] = { "0","1","2","3","4","5","6","7","8","9" };
		int c = *nextTerminal - 48;
		nextTerminal++;
		return makeTree1("D", 0, makeRoot(str[c], 1));
	}
	else {
		return NULL;
	}
}

TreeNode* N() {
	TreeNode* dig = D();
	if (dig) {
		TreeNode* nt = NT();
		if (nt) {
			return makeTree2("N", 0, dig, nt); 
		}
		else { return NULL; }
	}
	else{ return NULL; }
}

TreeNode* NT() {
	if (*nextTerminal == '\0' || *nextTerminal == '*' || *nextTerminal == '/' || *nextTerminal == ')' || *nextTerminal == '+' || *nextTerminal == '-') {
		return makeTree1("NT", 0, makeRoot("e", 1));
	}
	else {
		TreeNode* tail = N();
		if (tail) {
			return makeTree1("NT", 0, tail);
		}
		else { return NULL; }
	}
}

TreeNode* F() {
	if (*nextTerminal == '(') {
		nextTerminal++;
		TreeNode* e = E();
		if (e && *nextTerminal == ')') {
			nextTerminal++; //consumer ')'
			return makeTree3("F", 0, makeRoot("(", 1), e, makeRoot(")",1));
		}
		else { return NULL; }
	}
	else { 
		TreeNode* n = N();
		if (n) { 
			return makeTree1("F", 0, n); 
		}
		else { return NULL; }
	}
}

TreeNode* FT() {
	if (*nextTerminal == '\0' || *nextTerminal == '+' || *nextTerminal == '-' || *nextTerminal == ')') {
		return makeTree1("FT", 0, makeRoot("e", 1));
	}
	else {
		if (*nextTerminal == '*' || *nextTerminal == '/') {
			TreeNode* t1;
			if (*nextTerminal == '*') {
				t1 = makeRoot("*", 1);
			}
			else {
				t1 = makeRoot("/", 1);
			}
			nextTerminal++; //Consume
			TreeNode* t2 = F();
			if (t2) {
				TreeNode* t3 = FT();
				if (t3) {
					return makeTree3("FT", 0, t1, t2, t3);
				}
			}
			return NULL;
		}
		else { return NULL; }
	}
}

TreeNode* T() {
	TreeNode* t1 = F();
	if (t1) {
		TreeNode* t2 = FT();
		if (t2) { return makeTree2("T", 0, t1, t2); }
	}
	return NULL;
}

TreeNode* TT() {
	if (*nextTerminal == '\0' || *nextTerminal == ')') { return makeTree1("TT", 0, makeRoot("e", 1)); }
	else {
		if (*nextTerminal == '+' || *nextTerminal == '-') {
			TreeNode* t1;
			if (*nextTerminal == '+') { t1 = makeRoot("+", 1); }
			else { t1 = makeRoot("-", 1); }
			nextTerminal++; //Consume
			TreeNode* t2 = T();
			if (t2) {
				TreeNode* t3 = TT();
				if (t3) {
					return makeTree3("TT", 0, t1, t2, t3);
				}
			}
			return NULL;
		}
		else { return NULL; }
	}
}

TreeNode* E() {
	TreeNode* t1 = T();
	if (t1) {
		TreeNode* t2 = TT();
		if(t2){ 
			return makeTree2("E", 0, t1, t2); 
		}
	}
	return NULL;
}

//Prod rule for E
void EProd(Stack* s, TreeNode* t) {
	push(s, "TT");
	push(s, "T");
	TreeNode* build = getLeftmostNonTerminal(t);
	build->leftChild = makeRoot("T", 0);
	build->leftChild->rightSibling = makeRoot("TT", 0);
}

//Prod rule for non-epsilon TT
void TTProd(Stack* s, TreeNode* t, int c) {
	TreeNode* build = getLeftmostNonTerminal(t);
	push(s, "TT");
	push(s, "T");
	if (c == 2) { 
		push(s, "+"); 
		build->leftChild = makeRoot("+", 1);
	}
	else { 
		push(s, "-"); 
		build->leftChild = makeRoot("-", 1);
	}
	build->leftChild->rightSibling = makeRoot("T", 0);
	build->leftChild->rightSibling->rightSibling = makeRoot("TT", 0);
}

//Prod rule for non-epsilon FT
void FTProd(Stack* s, TreeNode* t, int c) {
	TreeNode* build = getLeftmostNonTerminal(t);
	push(s, "FT");
	push(s, "F");
	if (c == 6) {
		push(s, "*");
		build->leftChild = makeRoot("*", 1);
	}
	else {
		push(s, "/");
		build->leftChild = makeRoot("/", 1);
	}
	build->leftChild->rightSibling = makeRoot("F", 0);
	build->leftChild->rightSibling->rightSibling = makeRoot("FT", 0);
}

//Prod rule for T
void TProd(Stack* s, TreeNode* t) {
	push(s, "FT");
	push(s, "F");
	TreeNode* build = getLeftmostNonTerminal(t);
	build->leftChild = makeRoot("F", 0);
	build->leftChild->rightSibling = makeRoot("FT", 0);
}

//Prod rule for F->N or NT->N
void FNProd(Stack* s, TreeNode* t) {
	push(s, "N");
	TreeNode* build = getLeftmostNonTerminal(t);
	build->leftChild = makeRoot("N", 0);
}

//Prod rule for (E)
void parenthesis(Stack* s, TreeNode* t) {
	push(s, ")");
	push(s, "E");
	push(s, "(");
	TreeNode* build = getLeftmostNonTerminal(t);
	build->leftChild = makeRoot("(", 1);
	build->leftChild->rightSibling = makeRoot("E", 0);
	build->leftChild->rightSibling->rightSibling = makeRoot(")", 1);
}

//Prod rule for N
void NProd(Stack* s, TreeNode* t) {
	push(s, "NT");
	push(s, "D");
	TreeNode* build = getLeftmostNonTerminal(t);
	build->leftChild = makeRoot("D", 0);
	build->leftChild->rightSibling = makeRoot("NT", 0);
}

//Epsilon production rules
void epsilonProd(Stack* s, TreeNode* t) {
	TreeNode* build = getLeftmostNonTerminal(t);
	build->leftChild = makeRoot("e", 1);
}

//Prod rule for D
void DProd(Stack* s, TreeNode* t, int c) { //c is the digit 
	char* str[10] = {"0","1","2","3","4","5","6","7","8","9"};
	push(s, str[c]);
	TreeNode* build = getLeftmostNonTerminal(t);
	build->leftChild = makeRoot(str[c], 1);
}

int isTerminal(char* c) {
	return (c[0] == '+' || c[0] == '-' || c[0] == '*' || c[0] == '/' || (c[0] > 47 && c[0] < 58) || c[0] == ')' || c[0] == '('|| c[0] == 'e');
}

TreeNode* fail(Stack* s, TreeNode* t) {
	freeStack(s);
	freeTreeNode(t);
	return NULL;
}

int hashT(char c) {
	const char* str = "0123456789\0+-*/()";
	for (int i = 0; i < 17; i++) {
		if (c == str[i])
			return i;
	}
	return -1;
}

int hashNT(char* c) {
	const char *strings[] = { "E", "TT", "T", "FT", "F", "N", "NT", "D" };
	for (int i = 0; i < 8; i++) {
		if (strcmp(c, strings[i]) == 0)
			return i;
	}
	return -1;
}

TreeNode* tableDriver() {
	Stack* parse = new_Stack();
	push(parse, "\0");
	push(parse, "E");
	TreeNode* pt = makeRoot("E", 0);

	while (strcmp(peek(parse), "\0") != 0) {
		int lookahead = hashT(*nextTerminal);
		int stackTop = hashNT(peek(parse));
		int prod = parseTable(stackTop, lookahead);

		if (lookahead == -1) { return fail(parse, pt); } //Invalid terminal

		if (isTerminal(peek(parse))) {
			if (nextTerminal[0] == peek(parse)[0]) { //Match lookahead to terminal on top of stack
				pop(parse);
				nextTerminal++;
			}
			else { return fail(parse, pt); } //Fail
		}
		else if (prod != 0) { //Entry in parse table in non-zero
			pop(parse);
			if (prod > 13 && prod < 24) {
				DProd(parse, pt, *nextTerminal - 48); //Use digit prod. rule
			}
			else {
				switch (prod)
				{
				case 1:
					EProd(parse, pt);
					break;
				case 2:
					TTProd(parse, pt, 2);
					break;
				case 3:
					TTProd(parse, pt, 3);
					break;
				case 4:
					epsilonProd(parse, pt);
					break;
				case 5:
					TProd(parse, pt);
					break;
				case 6:
					FTProd(parse, pt, 6);
					break;
				case 7:
					FTProd(parse, pt, 7);
					break;
				case 8:
					epsilonProd(parse, pt);
					break;
				case 9:
					FNProd(parse, pt);
					break;
				case 10:
					parenthesis(parse, pt);
					break;
				case 11:
					NProd(parse, pt);
					break;
				case 12:
					FNProd(parse, pt);
					break;
				case 13:
					epsilonProd(parse, pt);
					break;
				}
			}
		}
		else { return fail(parse, pt); }
	}
	if (*nextTerminal == '\0') { 
		freeStack(parse);
		return pt;
	}
	else { return fail(parse, pt); }
}

void main() {
	FILE *file;
	file = fopen("testexp.txt", "r");
	char line[100];
	if (file == NULL) { perror("FileNotFound"); }
	else {
		while (fgets(line, 100, file)) {

			int len = strlen(line);
			nextTerminal = (char*)malloc(len*sizeof(char));
			strcpy(nextTerminal, line);
			if (nextTerminal[len-1] != '\0') {
				nextTerminal[len-1] = '\0';
			}
			printf("THE EXPRESSION IS: %s\n", nextTerminal);
			char* s = (char*)malloc(sizeof(nextTerminal));
			strcpy(s, nextTerminal);
			parseTreeNode = E();
			if (parseTreeNode) {
				printTreeNode(parseTreeNode);
				printf("%s = %f\n", s, evalTree(parseTreeNode));
				freeTreeNode(parseTreeNode);
				printf("Press enter to see table driven parse tree.");
				getchar();
				nextTerminal = s;
				parseTreeNode2 = tableDriver();
				printTreeNode(parseTreeNode2);
				getchar();
				freeTreeNode(parseTreeNode2);
			}
			else {
				printf("Parsing Failed.");
				getchar();
			}
			printf("\n");
		}
		fclose(file);
	}
}