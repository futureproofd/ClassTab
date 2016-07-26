package to.marcus.classtab.util;

/**
 * Created by mplienegger on 7/21/2016
 */
public class StringUtils {

    public static String escapeSpecialChars( String value, boolean quote )
    {
        StringBuilder builder = new StringBuilder();
        if( quote )
            builder.append( "\"" );
        for( char c : value.toCharArray() )
        {
            if( c == '\'' )
                builder.append( "\\'" );
            else if ( c == '\"' )
                builder.append( "\\\"" );
            else if( c == '\r' )
                builder.append( "\\r" );
            else if( c == '\n' )
                builder.append( "\\n" );
            else if( c == '\t' )
                builder.append( "\\t" );
            else if( c < 32 || c >= 127 )
                builder.append( String.format( "\\u%04x", (int)c ) );
            else
                builder.append( c );
        }
        if( quote )
            builder.append( "\"" );
        return builder.toString();
    }
}
