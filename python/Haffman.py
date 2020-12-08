#Tree node/or base node
class Node:
    data   : str
    weight : int
    left   : None
    right  : None

    def __init__(self, data, weight, left, right):
        self.data = data
        self.weight = weight
        self.left = left
        self.right = right

    def getCharBitcode(self, data, parentbit):
        if self.data == data: 
            return parentbit
        else:
            if self.left != None:
                bit = self.left.getCharBitcode(data, parentbit + str(0))
                if bit != None and bit != "": 
                    return bit
            if self.right != None:
                bit = self.right.getCharBitcode(data, parentbit + str(1))
                if bit != None and bit != "":
                    return bit
        return None #Means we dont find usable path

    #Sort node by weight
    def sort(self): 
        return self.weight 

class Huffman:
    tree    : Node
    bitmap  : dict
    encoded : str
    decoded : str
    oneType = False

    def __init__(self):
        pass

    def sortNode(self, node):
        return node.sort()

    def getCharChances(self, data):
        chancesMap = dict()
        for ch in data:
            chance = 1
            if chancesMap.get(ch) != None:
                chance = chancesMap.get(ch) + 1
            chancesMap[ch] = chance
        return chancesMap

    def encodeData(self, data):
        if len(data) <= 0:
            return ""

        # Add nodes for each char
        nodes = list()
        chancesMap = self.getCharChances(data)
        for ch in chancesMap.keys():
            nodes.append(Node(ch, chancesMap.get(ch), None, None))

        # In case if data has one type of char
        if len(nodes) == 1:
            self.oneType = True

        # Build a tree from nodes using Huffman algoritm
        while len(nodes) > 1:
            nodes.sort(key = self.sortNode, reverse = True)

            left = nodes.pop(len(nodes) - 1)
            right = nodes.pop(len(nodes) - 1)
            nodes.append(Node(0, right.weight + left.weight, left, right))
        self.tree = nodes[0]

        # In bitmap put the char and its bits/count
        self.bitmap = dict() #sortedDict by min
        for ch in chancesMap.keys():
            bit = ""
            if self.oneType:
                bit = "0" 
            self.bitmap[ch] = self.tree.getCharBitcode(ch, bit)

        # Encode each char in bit string
        self.encoded = ""
        for ch in data:
            self.encoded += self.bitmap.get(ch)
        print(chancesMap)
        return self.encoded

    def decodeData(self, data, tree):
        if tree == None or str(type(tree)) != "<class '__main__.Node'>":
            print("NoneTypeError: Tree is not set or not 'Node' type!") 
            return None
        
        node = tree
        self.decoded = ""
        #Decode each char from bit in to char string
        for ch in data:
            if self.oneType:
                self.decoded += node.data
                node = tree
                continue
            
            if ch == '0': 
                node = node.left 
            else: 
                node = node.right

            if node.data != 0:
                self.decoded += node.data
                node = tree
        return self.decoded

def main():
    while True:
        print("\nВведите строку: \n")
        data = input()
        bits = len(data) * 8

        huf = Huffman()
        encoded = huf.encodeData(data)
        decoded = huf.decodeData(encoded, huf.tree)
        print("====================")
        print("Строка оригинал: " + data)
        print("Вес: " + str(bits) + " бит")
        print("====================")
        print("Закодированная строка: " + encoded)
        print("Бит на символ: " + str(huf.bitmap))
        print("Вес: " + str(len(encoded)) + " бит")
        print("====================")
        print("Раскодированная строка: " + decoded)
        print("Вес: " + str(len(decoded) * 8) + " бит")
        print("====================")
main()