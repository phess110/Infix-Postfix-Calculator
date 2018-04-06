/*
* File: Stack.h
* Creator: Peter Hess
* Created: Oct 9 2017
*/

#ifndef _STACK_h
#define _STACK_h

//Stack node
struct Node {
	char* data;
	struct Node* next;
};

//Stack data structure
typedef struct Stack {
	struct Node* head;
}Stack;

extern Stack* new_Stack();			//Stack constructor
extern void push(Stack*, char*);	//Add string on top of stack
extern char* pop(Stack*);			//Remove & return item on top of stack
extern char* peek(Stack*);			//Return item on top of stack
extern void printStack(Stack*);		//Print stack contents
extern void freeStack(Stack*);		//Stack destructor
#endif