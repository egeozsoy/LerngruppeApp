package cp.chatapp;

/**
 * Created by Chantal on 19.09.2017.
 */

public class Message {

    private int _id;
    private String _date;
    private String _text;
    private int _sender;

    public Message(){};

    public Message (String _date, String _text, int _sender) {
        this._date = _date;
        this._text = _text;
        this._sender = _sender;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public String get_text() {
        return _text;
    }

    public int get_sender() {
        return _sender;
    }

    public void set_sender(int _sender) {
        this._sender = _sender;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_date() {
        return _date;
    }
}
