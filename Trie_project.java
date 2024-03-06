import java.util.*;
import java.io.*;

class TNode
{
    boolean isEOW;
    TNode[] children;
    public TNode()
    {
        this.isEOW=false;
        children=new TNode[26];
    }
}
class Trie
{
    TNode root=new TNode();
    void insertWord(String word)
    {
        TNode temp=root;
        for(int i=0;i<word.length();i++)
        {
            char ch=word.charAt(i);
            int idx=ch-'a';
            if(temp.children[idx]==null)
            {
                TNode newNode=new TNode();
                temp.children[idx]=newNode;
            }
            temp=temp.children[idx];
        }
        temp.isEOW=true;
    }
    boolean hasWord(String word)
    {
        TNode temp=root;
        for(int i=0;i<word.length();i++)
        {
            char ch=word.charAt(i);
            int idx=ch-'a';
            if(temp.children[idx]==null)
            {
                return false;
            }
            temp=temp.children[idx];
        }
        return temp.isEOW;
    }
    List<String> getAllWords()
    {
        List<String> ans=new ArrayList<>();
        StringBuilder path=new StringBuilder();
        helper(root,path,ans);
        return ans;
    }
    void helper(TNode root,StringBuilder path,List<String> ans)
    {
        if(root.isEOW)
        ans.add(path.toString());
        
        for(int i=0;i<26;i++)
        {
            if(root.children[i]!=null)
            {
                char ch=(char)(i+'a');
                path.append(ch);
                helper(root.children[i],path,ans);
                path.setLength(path.length()-1);
            }
        }
    }
    List<String> autoSuggest(String prefix)
    {
        List<String> ans=new ArrayList<>();
        TNode temp=root;
        for(char ch:prefix.toCharArray())
        {
            int idx=ch-'a';
            if(temp.children[idx]==null)
            return ans;
            temp=temp.children[idx];
        }
        StringBuilder path=new StringBuilder(prefix);
        helper(temp,path,ans);
        return ans;
    }
    public static void main(String args[]) throws Exception
    {
        Trie tr=new Trie();
        File f=new File("dict.txt");
        Scanner scf=new Scanner(f);
        
        
        Scanner sc=new Scanner(System.in);
        System.out.println("1. Insert a word");
        System.out.println("2. Insert all the words from a file");
        System.out.println("3. Check if given word exists");
        System.out.println("4. Check if given word exists from a file");
        System.out.println("5. Print all the words");
        System.out.println("6. Print all the words from a given prefix");
        System.out.println("7. Exit");
        
        while(true)
        {
            System.out.println("Enter your choice:");
            
            int choice=sc.nextInt();
            switch(choice)
            {
                case 1:
                    System.out.println("Enter the word:");
                    String word=sc.next();
                    tr.insertWord(word);
                    System.out.println("word inserted");
                    break;
                case 2:
                    while(scf.hasNextLine())
                    {
                        String word4=scf.next();
                        tr.insertWord(word4);
                    }
                    System.out.println("words inserted");
                    break;
                case 3:
                    System.out.println("Enter the word to search:");
                    String word1=sc.next();
                    System.out.println(tr.hasWord(word1));
                    break;
                case 4:
                    while(scf.hasNextLine())
                    {
                        System.out.println("Inserting all words from a file");
                        String word3=scf.next();
                        System.out.println(tr.hasWord(word3));
                    }
                    break;
                case 5:
                    System.out.println("All words from file are:");
                    for(String x:tr.getAllWords())
                    {
                        System.out.println(x);
                    }
                    break;
                case 6:
                        System.out.println("Enter prefix:");
                        String pref=sc.next();
                        for(String x:tr.autoSuggest(pref))
                        {
                        System.out.print(x+", ");
                        }
                        System.out.println();
                    break;
                default:
                  System.out.println("Wrong choice");
                  break;
            }
        }
        
       
        
        
    }
}