import tester.Tester;

// to represent a Deque
class Deque<T> {
  Sentinel<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // returns the size of the deque by counting all the nodes
  int size() {
    return this.header.size();
  }

  // adds new node containing data at the head of the deque
  void addAtHead(T data) {
    this.header.addAtHead(data);
  }

  // adds new node containing data at the tail of the deque
  void addAtTail(T data) {
    this.header.addAtTail(data);

  }

  // removes the head node from the deque
  T removeFromHead() {
    return this.header.removeFromHead();
  }

  // removes the tail node from the deque
  T removeFromTail() {
    return this.header.removeFromTail();
  }

  // finds the first node that is true for the predicate
  ANode<T> find(IPred<T> pred) {
    return this.header.find(pred);
  }

  // removes the specified node from the deque
  void removeNode(ANode<T> anode) {
    this.header.removeNode(anode);
  }
}

abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  abstract int sizeHelper();

  abstract T removeHelper();

  abstract ANode<T> find(IPred<T> pred);

  abstract ANode<T> findHelper(IPred<T> pred);

  abstract void removeNode(ANode<T> anode);

  abstract boolean isSentinel();

}

//to represent a sentinel header
class Sentinel<T> extends ANode<T> {

  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  // returns the size of the deque by counting all the nodes
  int size() {
    return this.next.sizeHelper();
  }

  // helper function for size, gives the base case
  int sizeHelper() {
    return 0;
  }

  // adds new node containing data at the head of the deque
  void addAtHead(T data) {
    new Node<T>(data, this.next, this);

  }

  // adds new node containing data at the tail of the deque
  void addAtTail(T data) {
    new Node<T>(data, this, this.prev);

  }

  // removes the head node from the deque
  T removeFromHead() {
    return this.next.removeHelper();
  }

  // removes the tail node from the deque
  T removeFromTail() {
    return this.prev.removeHelper();
  }

  // helper function that removes nodes
  T removeHelper() {
    throw new RuntimeException("Cannot do this");

  }

  // finds the first node that is true for the predicate
  ANode<T> find(IPred<T> pred) {
    return this.next.findHelper(pred);
  }

  // helper method for find
  ANode<T> findHelper(IPred<T> pred) {
    return this;
  }

  // removes the specified node from the deque
  void removeNode(ANode<T> anode) {
    this.next.removeNode(anode);

  }

  // checks if the given ANode is a sentinel
  boolean isSentinel() {
    return true;
  }

}

// to represent a Node
class Node<T> extends ANode<T> {
  T data;

  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  Node(T data, ANode<T> next, ANode<T> prev) {
    this.data = data;
    this.next = next;
    this.prev = prev;

    if (next == null || prev == null) {
      throw new IllegalArgumentException();
    } else {
      this.next.prev = this;
      this.prev.next = this;
    }
  }

  // helper function for size, adds one for each node
  int sizeHelper() {
    return 1 + this.next.sizeHelper();
  }

  // helper function that removes nodes
  T removeHelper() {
    this.prev.next = this.next;
    this.next.prev = this.prev;
    return this.data;
  }

  // finds the first node that is true for the predicate
  ANode<T> find(IPred<T> pred) {
    return this.findHelper(pred);
  }

  // helper method for find
  ANode<T> findHelper(IPred<T> pred) {
    if (pred.apply(this.data)) {
      return this;
    } else {
      return this.next.findHelper(pred);
    }
  }

  // removes the given node from the deque
  void removeNode(ANode<T> anode) {
    if (anode.isSentinel()) {
      return;
    } else {
      Node<T> node = (Node<T>) anode;
      if (node.data.equals(this.data)) {
        this.prev.next = this.next;
        this.next.prev = this.prev;
      } else {
        this.next.removeNode(anode);
      }
    }
  }

  // return false because node is not a sentinel
  boolean isSentinel() {
    return false;
  }

}

// predicate interface
interface IPred<T> {
  boolean apply(T t);
}

// class to represent strings equal to 3
class StringLength3 implements IPred<String> {

  // apply method to return true if the given string is equal to 3
  public boolean apply(String data) {
    return data.length() == 3;
  }
}

// Examples class for Deque
class ExamplesDeque {

  Deque<String> deque1;
  Deque<String> deque2;
  Deque<String> deque3;
  Deque<String> deque4;

  Sentinel<String> sentinel1;
  Sentinel<String> sentinel2;
  Sentinel<String> sentinel3;

  ANode<String> n1;
  ANode<String> n2;
  ANode<String> n3;
  ANode<String> n4;
  ANode<String> n5;
  ANode<String> n6;
  ANode<String> n7;
  ANode<String> n8;
  ANode<String> n9;
  ANode<String> n10;

  void initDeque() {
    sentinel1 = new Sentinel<String>();
    sentinel2 = new Sentinel<String>();
    sentinel3 = new Sentinel<String>();

    n1 = new Node<String>("abc", sentinel1, sentinel1);
    n2 = new Node<String>("bcd", sentinel1, n1);
    n3 = new Node<String>("cde", sentinel1, n2);
    n4 = new Node<String>("def", sentinel1, n3);

    deque1 = new Deque<String>();

    deque2 = new Deque<String>(this.sentinel1);

    n5 = new Node<String>("lol", sentinel3, sentinel3);
    n6 = new Node<String>("kom", sentinel3, n5);
    n7 = new Node<String>("bob", sentinel3, n6);
    n8 = new Node<String>("nil", sentinel3, n7);
    n9 = new Node<String>("pey", sentinel3, n8);

    deque3 = new Deque<String>(this.sentinel3);

  }

  // tests the method size
  void testSize(Tester t) {
    this.initDeque();
    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.sentinel1.size(), 4);
    t.checkExpect(this.deque3.size(), 5);
    t.checkExpect(this.sentinel3.size(), 5);

  }

  // tests the method addAtHead
  void testAddAtHead(Tester t) {
    this.initDeque();
    this.deque1.addAtHead("def");
    this.deque1.addAtHead("cde");
    this.deque1.addAtHead("bcd");
    this.deque1.addAtHead("abc");

    t.checkExpect(deque1, deque2);

  }

  // tests the method addToTail
  void testAddAtTail(Tester t) {
    this.initDeque();
    // if you add the elements in the list in the opposite order but add using
    // addAtTail
    // then it will return the same as the full original list
    this.deque1.addAtTail("abc");
    this.deque1.addAtTail("bcd");
    this.deque1.addAtTail("cde");
    this.deque1.addAtTail("def");

    t.checkExpect(deque1, deque2);

  }

  // tests the method removeFromHead
  void testRemoveFromHead(Tester t) {
    this.initDeque();
    this.deque1.addAtHead("def");
    this.deque1.addAtHead("cde");
    this.deque1.addAtHead("bcd");
    this.deque1.addAtHead("abc");
    this.deque1.addAtHead("lol");
    this.deque1.removeFromHead();

    t.checkExpect(deque1, deque2);

    this.initDeque();

    t.checkException(new RuntimeException("Cannot do this"), deque1.header, "removeFromHead");
  }

  // tests the method removeFromTail
  void testRemoveFromTail(Tester t) {
    this.initDeque();
    this.deque1.addAtHead("def");
    this.deque1.addAtHead("cde");
    this.deque1.addAtHead("bcd");
    this.deque1.addAtHead("abc");
    this.deque1.addAtTail("lol");
    this.deque1.removeFromTail();

    t.checkExpect(deque1, deque2);

    this.initDeque();

    t.checkException(new RuntimeException("Cannot do this"), deque1.header, "removeFromTail");

  }

  // tests the method IPred -> for StringLength3
  void testIPred(Tester t) {
    this.initDeque();
    this.deque1.addAtHead("def");
    this.deque1.addAtHead("cde");
    this.deque1.addAtHead("bcd");
    this.deque1.addAtHead("abc");

    t.checkExpect(deque1.find(new StringLength3()), this.deque1.header.next);

    this.initDeque();
    this.deque1.addAtHead("def");
    this.deque1.addAtHead("cde");
    this.deque1.addAtHead("bcd");
    this.deque1.addAtHead("abcd");

    t.checkExpect(deque1.find(new StringLength3()), this.deque1.header.next.next);

    this.initDeque();
    this.deque1.addAtHead("defz");
    this.deque1.addAtHead("cdez");
    this.deque1.addAtHead("bcdz");
    this.deque1.addAtHead("abcd");

    t.checkExpect(this.deque1.find(new StringLength3()), this.deque1.header);

  }

  // tests for the method removeNode
  void testRemoveNode(Tester t) {
    this.initDeque();
    this.deque1.addAtHead("def");
    this.deque1.addAtHead("cde");
    this.deque1.addAtHead("bcd");
    this.deque2.removeNode(n1);

    t.checkExpect(deque2, deque1);

    this.initDeque();
    this.deque1.addAtHead("def");
    this.deque1.addAtHead("cde");
    this.deque1.addAtHead("bcd");
    this.deque1.addAtHead("abc");
    this.deque2.removeNode(deque2.header);

    t.checkExpect(deque1, deque2);

  }

}
