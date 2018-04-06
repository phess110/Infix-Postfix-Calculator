/*
* File: Stack.h
* Creator: Peter Hess
* Created: Oct 9 2017
*/

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "Stack.h"

Stack* new_Stack() {
	Stack* newStack = (Stack*) malloc(sizeof(Stack));
	newStack->head = NULL;
	return newStack;
}

void push(Stack* s, char* x) {
	struct Node* newHead = (struct Node*) malloc(sizeof(struct Node));
	newHead->data = strdup(x);
	newHead->next = NULL;
	if (s->head) {
		newHead->next = s->head;
		s->head = newHead;
	}
	else {
		s->head = newHead;
	}
}

char* pop(Stack* s) {
	if (s && s->head) {
		struct Node *deletedNode = s->head;
		char* deletedVal = deletedNode->data;
		(s->head) = (s->head)->next;
		free(deletedNode);
		return deletedVal;
	}
	else { return '\0'; }
}

char* peek(Stack* s) {
	if (s && s->head) { return s->head->data; }
	else { return '\0'; }
}

//print stack helper
void printS(struct Node* s) {
	if (s) {
		printf("%s\n", s->data);
		if (s->next) {
			printS(s->next);
		}
	}
	else {
		printf("The stack is empty.\n");
	}
}

void printStack(Stack* s) {
	printS(s->head);
}

//Recursively frees stack nodes
void clear(Stack* s) {
	if (s && s->head) {
		struct Node *deletedNode = s->head;
		(s->head) = (s->head)->next;
		free(deletedNode);
		clear(s);
	}
}

void freeStack(Stack* s) {
	clear(s);
	free(s);
}