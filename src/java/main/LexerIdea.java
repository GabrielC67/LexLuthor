interface LexerIdea {
    void setTextBuffer(String buffer);
    int getCurrentOffset();
    int getCurrentLineNumber();
    Token getNextToken();
}