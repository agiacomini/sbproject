package com.andr3a.giacomini.sbproject.utils;

import com.andr3a.giacomini.sbproject.model.dto.BaseDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Comparator;

public class Utils {

    //- STRING

    public static final boolean isNullOr0Len( String s ) {
        return s == null || s.length() == 0 ;
    }

    public static final boolean isNullOrTrimmedEmpty( String s ) {
        return s == null || s.trim().length() == 0 ;
    }

    public static final boolean isNotNullAndNotTrimmedEmpty( String s ) {
        return ! isNullOrTrimmedEmpty( s ) ;
    }

    //- NOT STRING

    public static final <T> int safeCompare( T t1, T t2, Comparator<T> aComparator ) {
        if ( t1 == null && t2 == null ) return 0 ;
        if ( t1 == null ) return -1 ;
        if ( t2 == null ) return 1 ;
        return aComparator.compare( t1, t2 ) ;
    }

    public static final <T extends Comparable<T>> int safeCompare( T t1, T t2 ) {
        if ( t1 == null && t2 == null ) return 0 ;
        if ( t1 == null ) return -1 ;
        if ( t2 == null ) return 1 ;
        return t1.compareTo( t2 ) ;
    }

    public static final <T extends Comparable<T>> boolean safeEquals( T t1, T t2 ) {
        return safeCompare( t1, t2 ) == 0 ;
    }

    public static final <T> boolean safeEquals( T t1, T t2 ) {
        if ( t1 == null && t2 == null ) return true ;
        if ( t1 == null || t2 == null ) return false ;
        return t1.equals( t2 ) ;
    }

    //- PASSWORD
    public static boolean checkUserPassword(String password,
                                            String userPassword,
                                            PasswordEncoder passwordEncoder){
        // PasswordEncoder like BCryptPasswordEncoder
        return passwordEncoder.matches(password, userPassword);
    }

    public BaseDto convertToDto(Object obj, BaseDto mapper) {
        return new ModelMapper().map(obj, mapper.getClass());
    }

    public Object convertToEntity(Object obj, BaseDto mapper) {
        return new ModelMapper().map(mapper, obj.getClass());
    }
}
