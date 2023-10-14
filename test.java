import java.util.ArrayList;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        String method,name,bookName;
        MemberTrie memberTrie = new MemberTrie();
        BookTrie bookTrie = new BookTrie();
        long start,end;
        int count;
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            method=in.next();
            switch (method){
                case "arrive" :
                    name=in.next();
                    start=in.nextLong();
                    arrive(memberTrie,name,start);
                    break;
                case "exit" :
                    name=in.next();
                    end=in.nextLong();
                    exit(memberTrie,name,end);
                    break;
                case "isInLib" :
                    name=in.next();
                    isnLib(memberTrie,name);
                    break;
                case "returnBook" :
                    name=in.next();
                    bookName=in.next();
                    returnBook(memberTrie,bookTrie,name,bookName);
                    break;
                case "totalTimeInLib" :
                    name=in.next();
                    start=in.nextLong();
                    end=in.nextLong();
                    totalTimeInLib(memberTrie,name,start,end);
                    break;
                case "addNewBook" :
                    bookName=in.next();
                    count=in.nextInt();
                    addNewBook(bookTrie,bookName,count);
                    break;
                case "shouldBring" :
                    bookName=in.next();
                    name=in.next();
                    shouldBring(memberTrie,bookTrie,bookName,name);
                    break;
                case "allPersonCurrentBook" :
                    name=in.next();
                    allPersonCurrentBook(memberTrie,name);
                    break;
                case "allPersonHave" :
                    bookName=in.next();
                    allPersonHave(bookTrie,bookName);

            }

        }

    }
    static void isnLib(MemberTrie memberTrie, String name){
        if (memberTrie.find(name)){if (memberTrie.findMemberEndNode(name).member.stillThere)
            System.out.println("YES");
        else
            System.out.println("NO");}
        else System.out.println("NO");

    }
    static void arrive(MemberTrie memberTrie, String name,long arivetime){
        memberTrie.push(name);
        memberTrie.findMemberEndNode(name).member.hours.add(arivetime);
        memberTrie.findMemberEndNode(name).member.stillThere = true;
    }
    static void exit(MemberTrie memberTrie, String name,long exittime){
        if (memberTrie.find(name)) {
            memberTrie.findMemberEndNode(name).member.hours.add(exittime);
            memberTrie.findMemberEndNode(name).member.stillThere = false;
        }

    }
    static void addNewBook(BookTrie bookTrie, String BookName ,int count){
        bookTrie.push(BookName,count);
    }
    static void allPersonCurrentBook(MemberTrie memberTrie, String name){
        if (!memberTrie.find(name))
            System.out.println("empty");
        else {
            memberTrie.findMemberEndNode(name).member.borrowedBooks.inorder();
        }
    }
    static void allPersonHave(BookTrie bookTrie, String bookName){
        if (!bookTrie.find(bookName))
            System.out.println("empty");
        else {
            bookTrie.findEndNode(bookName).book.Borrower.inorder();
        }
    }
    static void totalTimeInLib(MemberTrie memberTrie, String name,long s ,long e){
        long sPrime,ePrime,v,k;
        long fin=0;
        k=0;

        for (int i=0;i<memberTrie.findMemberEndNode(name).member.hours.size()-1;i++){
            v=memberTrie.findMemberEndNode(name).member.hours.get(i);
            sPrime=v-s;
            if(sPrime<=0) sPrime=0;
            if(i+1!=memberTrie.findMemberEndNode(name).member.hours.size()) k=memberTrie.findMemberEndNode(name).member.hours.get(i+1);
            ePrime=e-k;
            if(ePrime<0 || i+1==memberTrie.findMemberEndNode(name).member.hours.size()) ePrime=0;
            long domain=e-s-sPrime-ePrime;
            fin+=domain;
            i++;
        }
        System.out.println(fin);
    }
    static void returnBook(MemberTrie memberTrie, BookTrie bookTrie, String name,String book) {
        if (bookTrie.find(book) && memberTrie.find(name)) {
            bookTrie.findEndNode(book).book.count++;
            bookTrie.findEndNode(book).book.state = true;
            bookTrie.findEndNode(book).book.Borrower.delete(name);
            memberTrie.findMemberEndNode(name).member.borrowedBooks.delete(book);
        }
    }
    static void shouldBring(MemberTrie memberTrie, BookTrie bookTrie, String book,String name){
        if (memberTrie.find(name) && bookTrie.find(book)) {
            if (memberTrie.findMemberEndNode(name).member.stillThere && bookTrie.findEndNode(book).book.state) {
                bookTrie.findEndNode(book).book.count--;
                if (bookTrie.findEndNode(book).book.count == 0)
                    bookTrie.findEndNode(book).book.state = false;
                bookTrie.findEndNode(book).book.Borrower.push(name);
                memberTrie.findMemberEndNode(name).member.borrowedBooks.push(book);
            }
        }
    }
}
class BST {
    BSTNode first;
    public void push(String string) {
        if (first == null) {
            first = new BSTNode(string);
            return;
        }
        BSTNode temp = first;
        while (true) {
            if (string.compareTo(temp.key) < 0) {
                if (temp.leftChild != null)
                    temp = temp.leftChild;
                else {
                    temp.leftChild = new BSTNode(string);
                    break;
                }
            } else if (string.compareTo(temp.key) > 0) {
                if (temp.rightChild != null)
                    temp = temp.rightChild;
                else {
                    temp.rightChild = new BSTNode(string);
                    break;
                }
            }
            else
                break;
        }
    }
    public void inorder() {
        if (first == null)
            System.out.println("empty");
        else
            ainorder(first);
    }
    private void ainorder(BSTNode t) {
        if (t != null) {
            ainorder(t.leftChild);
            System.out.print(t.key + " ");
            ainorder(t.rightChild);
            System.out.println();
        }
    }
    public BSTNode search(BSTNode root, String key)
    { if (root==null || root.key.equals(key))
        return root;
        if (key.compareTo(root.key)<0)
            return search(root.leftChild, key);
        return search(root.rightChild, key);
    }

    void delete(String key){
        first = deleteRec(first, key);
    }
    BSTNode deleteRec(BSTNode root, String key) {
        if (root == null)
            return root;
        if (key.compareTo(root.key) < 0)
            root.leftChild = deleteRec(root.leftChild, key);
        else if (root.key.compareTo(key) < 0)
            root.rightChild = deleteRec(root.rightChild, key);
        else {
            if (root.leftChild == null)
                return root.rightChild;
            else if (root.rightChild == null)
                return root.leftChild;
            root.key = min(root.rightChild);
            root.rightChild = deleteRec(root.rightChild, root.key);
        }

        return root;
    }
    String min(BSTNode root){
        String minv = root.key;
        while (root.leftChild != null) {
            minv = root.leftChild.key;
            root = root.leftChild;
        }
        return minv;
    }
    public class BSTNode {
        String key;
        BSTNode leftChild, rightChild;
        public BSTNode(String d) {
            key = d;
            leftChild = rightChild = null;
        }
    }
}
class BookTrie {
    final int ALPHABET_SIZE = 26;
    class TrieNode
    {
        TrieNode[] offSpring = new TrieNode[ALPHABET_SIZE];
        Book book;
        boolean isWord;

        TrieNode(){
            book=null;
            isWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++)
                offSpring[i] = null;
        }

    }
    public BookTrie(){
        this.first=new TrieNode();
    }
    TrieNode first;
    TrieNode findEndNode(String name){
        int length = name.length();
        int index;
        TrieNode temp = first;
        for (int i = 0; i < length; i++) {
            index = name.charAt(i) - 'a';
            if (temp.offSpring[index] == null) {
                temp.offSpring[index] = new TrieNode();
            }
            temp = temp.offSpring[index];
        }
        return temp;
    }
    void push(String key,int count)
    {
        int level;
        int length = key.length();
        int index;

        TrieNode pCrawl = first;

        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'a';
            if (pCrawl.offSpring[index] == null)
                pCrawl.offSpring[index] = new TrieNode();

            pCrawl = pCrawl.offSpring[index];
        }

        pCrawl.isWord = true;
        if (pCrawl.book == null)
            pCrawl.book=new Book(count);
        else {
            pCrawl.book.count = pCrawl.book.count + count;
        }
        if (pCrawl.book.count >= 1)
            pCrawl.book.state = true;
    }
    boolean find(String key)
    {
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = first;

        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'a';

            if (pCrawl.offSpring[index] == null)
                return false;

            pCrawl = pCrawl.offSpring[index];
        }

        return (pCrawl != null && pCrawl.isWord);
    }
    TrieNode del(TrieNode root, String key, int depth)
    {
        if (root != null)
            return null;

        if (depth == key.length()) {

            if (root.isWord)
                root.isWord = false;

            if (isEmpty(root)) {
                for(int i = 0 ; i<root.offSpring.length;i++){
                    root.offSpring[i]=null;
                }
                root.isWord = false;
                root = null;
            }

            return root;
        }

        int index = key.charAt(depth) - 97;
        root.offSpring[index] =
                del(root.offSpring[index], key, depth + 1);

        if (isEmpty(root) && root.isWord == false) {
            for(int i = 0 ; i<root.offSpring.length;i++){
                root.offSpring[i]=null;
            }
            root.isWord = false;
            root = null;
        }

        return root;
    }
    boolean isEmpty(TrieNode root)
    {
        for (int i = 0; i < ALPHABET_SIZE; i++)
            if (root.offSpring[i] != null)
                return false;
        return true;
    }

}
class MemberTrie {
    final int ALPHABET_SIZE = 26;
    class TrieNode
    {
        TrieNode[] offSpring = new TrieNode[ALPHABET_SIZE];
        Member member;
        boolean isWord;

        TrieNode(){
            member=null;
            isWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++)
                offSpring[i] = null;
        }
    }
    public MemberTrie() {
        this.first = new TrieNode();
    }
    TrieNode first;
    void push(String key)
    {
        int level;
        int length = key.length();
        int index;

        TrieNode pCrawl = first;

        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'a';
            if (pCrawl.offSpring[index] == null)
                pCrawl.offSpring[index] = new TrieNode();

            pCrawl = pCrawl.offSpring[index];
        }
        pCrawl.isWord = true;
        if (pCrawl.member == null)
            pCrawl.member = new Member();
        pCrawl.member.stillThere = true;
    }
    TrieNode findMemberEndNode(String word){
        int length = word.length();
        int index;
        TrieNode temp = first;
        for (int i = 0; i < length; i++) {
            index = word.charAt(i) - 'a';
            if (temp.offSpring[index] == null) {
                temp.offSpring[index] = new TrieNode();
            }
            temp = temp.offSpring[index];
        }
        return temp;
    }
    boolean find(String key)
    {
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = first;

        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'a';

            if (pCrawl.offSpring[index] == null)
                return false;

            pCrawl = pCrawl.offSpring[index];
        }

        return (pCrawl != null && pCrawl.isWord);
    }
    TrieNode del(TrieNode root, String key, int depth)
    {
        if (root != null)
            return null;

        if (depth == key.length()) {

            if (root.isWord)
                root.isWord = false;

            if (isEmpty(root)) {
                for(int i = 0 ; i<root.offSpring.length;i++){
                    root.offSpring[i]=null;
                }
                root.isWord = false;
                root = null;
            }

            return root;
        }

        int index = key.charAt(depth) - 97;
        root.offSpring[index] =
                del(root.offSpring[index], key, depth + 1);

        if (isEmpty(root) && root.isWord == false) {
            for(int i = 0 ; i<root.offSpring.length;i++){
                root.offSpring[i]=null;
            }
            root.isWord = false;
            root = null;
        }

        return root;
    }
    boolean isEmpty(TrieNode root)
    {
        for (int i = 0; i < ALPHABET_SIZE; i++)
            if (root.offSpring[i] != null)
                return false;
        return true;
    }

}
class Member {
    boolean stillThere;
    BST borrowedBooks;
    ArrayList<Long> hours;

    public Member() {
        stillThere=true;
        borrowedBooks =new BST();
        hours = new ArrayList<>();
    }

}
class Book {
    int count;
    BST Borrower;
    boolean state;

    public Book(int count) {
        this.count = count;
        if (this.count > 0)
            this.state = true;
        else
            this.state = false;
        Borrower = new BST();
    }
}