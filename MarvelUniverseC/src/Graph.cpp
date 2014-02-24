#include "Graph.h"
#include <iostream>
#include <fstream>
#include <cstdlib>
#include <queue>
#include <algorithm>

Graph::Graph()
{

}

Graph::Graph(char* filename)
{
    string line;
    ifstream myfile (filename);

    if (myfile.is_open())
    {

        while(getline (myfile,line))
        {
            get_line(line);
        }
        myfile.close();
    }
    else cout << "Unable to open file";
}

void Graph::get_line(string line)
{
    int pos = line.find_first_of('\"');
    line.erase(0,++pos);
    pos = line.find_first_of('\"');
    string vertex_name = line.substr(0,pos);


    line.erase(0,++pos);
    pos = line.find_first_of('\"');
    line.erase(0,++pos);
    pos = line.find_first_of('\"');
    string edge_name = line.substr(0,pos);

    add_line(vertex_name, edge_name);
}

void Graph::add_line(string character_name, string book_name)
{
    map<string, vector<string> >::iterator it = characters_to_books.find(character_name);
    if(it == characters_to_books.end()) //character does not exist
    {
        vector<string> new_vector;
        new_vector.push_back(book_name);
        characters_to_books.insert(pair<string,vector<string> >(character_name,new_vector) );
    }
    else //character is already there
    {
        it->second.push_back(book_name);
    }

    map<string, vector<string> >::iterator it2 = books_to_characters.find(book_name);
    if(it2 == books_to_characters.end()) //book does not exist
    {
        vector<string> new_vector2;
        new_vector2.push_back(character_name);
        books_to_characters.insert(pair<string,vector<string> >(book_name,new_vector2) );
    }
    else //book is already there
    {
        it2->second.push_back(character_name);
    }
}
/*Graph::~Graph()
{
    //dtor
}
*/

vector<string> Graph::get_path(string start, string dest)
{
    vector<string> myvector;
    map<string, vector<string> > mymap;
    queue<string> myqueue;

    myqueue.push(start);
    mymap.insert(pair<string, vector<string> >(start, myvector) );


    while(!myqueue.empty())
    {
        string next_char = myqueue.front();
        myqueue.pop();
        map<string, vector<string> >::iterator current_char_to_path = mymap.find(next_char);
                                   /* if(current_char_to_path == mymap.end())
                                    {
                                        cout<<"Error in current_char_to_path";
                                        return myvector;
                                    }*/
        if(next_char == dest) return current_char_to_path->second;

        map<string, vector<string> >::iterator current_char_to_booklist = characters_to_books.find(next_char);//current character and its books to add to queue
                                /*if(current_char_to_booklist == characters_to_books.end())
                                {
                                    cout<<"Error in current_char_to_booklist";
                                    return myvector;
                                }*/
        sort(current_char_to_booklist->second.begin(), current_char_to_booklist->second.end());

        for(vector<string>::iterator booklist = current_char_to_booklist->second.begin(); booklist != current_char_to_booklist->second.end(); booklist++)//iterating through books
        {
            map<string, vector<string> >::iterator book_to_charlist = books_to_characters.find(*booklist);
                            /*if(book_to_charlist == books_to_characters.end())
                            {
                                cout<<"Error in book_to_charlist";
                                return myvector;
                            }*/
            sort(book_to_charlist->second.begin(), book_to_charlist->second.end());

            for(vector<string>::iterator charlist = book_to_charlist->second.begin(); charlist != book_to_charlist->second.end(); charlist++)//iterating through characters
            {
                if(mymap.find(*charlist) == mymap.end())
                {
                    //books_to_characters.find(*it2);
                    vector<string> new_vector;
                    new_vector.insert(new_vector.end(),current_char_to_path->second.begin(),current_char_to_path->second.end());
                    new_vector.insert(new_vector.end(),*booklist);
                    mymap.insert(pair<string, vector<string> >(*charlist,new_vector));
                    myqueue.push(*charlist);

                }
            }
        }
    }


    return myvector;
}

int Graph::size()
{
    return characters_to_books.size();
}
