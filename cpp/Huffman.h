#pragma once

#include "iostream"
#include "memory"
#include "map"
#include "string"
#include "vector"
#include "set"
#include "algorithm"

using namespace std;
typedef uint16_t uint;

// The tree node, or left, right nodes
class Node
{
public:
	char data;
	uint weight;
	shared_ptr<Node> left, right;
public:
	Node(char data, uint weight, shared_ptr<Node> left = 0, shared_ptr<Node> right = 0);
	Node() = default;

public:
	string getCharBitcode(char data, string parentbit);
};

class Huffman
{
	bool oneType;
public:
	Node tree;
	map<char, string> bitmap;
	string encoded, decoded;
public:
	Huffman() = default;
	~Huffman() = default;

	string encodeData(const char* data);
	string decodeData(const char* encoded, const Node& tree);
	map<char, uint> getCharChances(const char* data, uint length);
	string getBitmap();
};