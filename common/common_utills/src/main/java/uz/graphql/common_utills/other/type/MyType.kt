package uz.graphql.common_utills.other.type


/**
 * Saidmurod Turdiyev writes this for All Project (SMT)
 */

typealias emptyBlock = () -> Unit
typealias sendOneParametreBlock <T> = (T) -> Unit
typealias sendTwoParametreBlock <T,K> = (T,K) -> Unit
typealias returnParametreBlock <T> = () -> T
typealias sendAndReturnParametreBlock <T> = (T) -> T
typealias sendAndReturnDifferentParametreBlock <T, K> = (T) -> K
