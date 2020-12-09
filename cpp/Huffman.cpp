
#include "Huffman.h"

Node::Node(char data, uint weight, shared_ptr<Node> left, shared_ptr<Node> right)
	: data(data), weight(weight), left(left), right(right) {}

string Node::getCharBitcode(char data, string parentbit)
{
	if (this->data == data) return parentbit;
	else {
		if (left != nullptr)
		{
			string bit = left->getCharBitcode(data, parentbit + "0");
			if(!bit.empty()) return bit;
		}
		if (right != nullptr)
		{
			string bit = right->getCharBitcode(data, parentbit + "1");
			if(!bit.empty()) return bit;
		}
	}
	return string();
}

map<char, uint> Huffman::getCharChances(const char* data, uint length)
{
	map<char, uint> chancesMap;
	for (int i = 0; i < length; i++)
	{
		//get the chance and add it to map
		char ch = data[i];
		int chance = chancesMap[ch] ? chancesMap[ch] + 1 : 1;
		chancesMap[ch] = chance;
	}
	return chancesMap;
}

string Huffman::getBitmap()
{
	string buf = "{";
	for (auto e : bitmap) {
		string item = "[" + string(1, e.first) + string(" = ") + string(e.second) + "], ";
		buf += item;
	}
	return buf += "}";
}

string Huffman::encodeData(const char* data)
{
	uint length = (uint)(size_t)strlen(data);
	if (length == 0) 
		return string();

	// Add nodes for each char
	vector<shared_ptr<Node>> nodes;
	map<char, uint> chancesMap = getCharChances(data, length);
	set<char> keySet; //C++ stuff: retrieving a keys from map to set
	for (auto kv : chancesMap)
		keySet.emplace(kv.first);
	for (char ch : keySet)
		nodes.push_back(make_shared<Node>(ch, chancesMap[ch]));

	// Case if in our data one type of char
	if (nodes.size() == 1)
		oneType = true;

	// Build a tree form nodes using Huffman algorithm
	while (nodes.size() > 1)
	{
		sort(nodes.begin(), nodes.end(), [](const shared_ptr<Node> a, const shared_ptr<Node> b) 
			{ return a->weight > b->weight; } );
		
		shared_ptr<Node> left = nodes[nodes.size() - 1];
		nodes.erase(nodes.end() - 1);
		shared_ptr<Node> right = nodes[nodes.size() - 1];
		nodes.erase(nodes.end() - 1);
		nodes.push_back(make_shared<Node>(0, right->weight + left->weight, left, right));
	}
	tree = *nodes[0];

	// In bitmap put the char and its bits/count
	bitmap = {};
	for (char ch : keySet)
		bitmap[ch] = oneType ? "0" : tree.getCharBitcode(ch, "");

	// Encode each char in bit string
	encoded = "";
	for (int i = 0; i < length; i++)
		encoded += bitmap[data[i]];

	return encoded;
}

string Huffman::decodeData(const char* encoded, const Node& tree)
{
	Node node = tree;
	decoded = "";
	// Decode each char form bit in to char string, include oneType situation
	for (int i = 0; i < strlen(encoded); i++) {
		if (oneType) {
			decoded += node.data;
			node = tree;
			continue;
		}

		node = (encoded[i] == '0') ? *node.left : *node.right;
		if (node.data != 0) {
			decoded += node.data;
			node = tree;
		}
	}
	return decoded;
}

int main()
{
	while (true) {
		Huffman huf;
		string data;
		cout << "Input your string: "; cin.ignore(numeric_limits<streamsize>::max(), '\n'); getline(cin, data, '\n');
		string encoded = huf.encodeData(data.c_str());
		string decoded = huf.decodeData(encoded.c_str(), huf.tree);

		cout << "==========================\n";
		cout << "Original string: " << data << "\n";
		cout << "Size: " << data.length() * 8 << " bits\n";
		cout << "==========================\n";
		cout << "Encoded string: " << encoded << "\n";
		cout << "Bits on char: " << huf.getBitmap() << "\n";
		cout << "Size: " << encoded.length() << " bits\n";
		cout << "==========================\n";
		cout << "Decoded string: " << decoded << "\n";
		cout << "Size: " << decoded.length() * 8 << " bits\n";
		cout << "==========================\n";
	}
	return 0;
}
