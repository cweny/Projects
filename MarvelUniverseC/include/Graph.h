#ifndef GRAPH_H
#define GRAPH_H
#include <string>
#include <map>
#include <vector>

using namespace std;

class Graph
{
    public:
        Graph(char*);
        void get_line(string);
        void add_line(string, string);
        vector<string> get_path(string, string);
        int size();
    protected:
    private:
        map<string, vector<string> > characters_to_books;
        map<string, vector<string> > books_to_characters;


};

#endif 
