

class SpecialHashMap() : HashMap<String, Int>() {
    class SpecialHashMapException(message: String): Exception(message)

    inner class Iloc() {
        operator fun get(k: Int): Int {
            var i = 0
            for ((key, value) in this@SpecialHashMap.toSortedMap()) {
                if (k == i) return value
                i++
            }
            throw SpecialHashMapException("invalid key number")
        }
    }
    inner class Ploc() {
        operator fun get(cond: String):HashMap<String, Int> {
            val result : HashMap<String, Int> = HashMap()
            //println(cond)
            var c = ""
            val cs : HashMap<Int, String> = HashMap()
            var csn = 0
            var posc = 0

            while (posc < cond.length) {
                while (posc < cond.length && (cond[posc] == '<' || cond[posc] == '>' || cond[posc] == '=' || cond[posc].isDigit())) {
                    c += cond[posc]
                    posc++
                }
                while (posc < cond.length && !cond[posc].isDigit() && !(cond[posc] == '<' || cond[posc] == '>' || cond[posc] == '=')) {
                    posc++
                }
                cs.put(csn, c)
                c = ""
                csn++
            }

            //println(csn)
            for ((key, value) in this@SpecialHashMap) {
                var to_put = true
                if (isNumbers(key, csn)) {
                    val ky = key.replace("(","").replace(")","")
                    val ks : HashMap<Int, String> = HashMap()
                    var d: String = ""
                    posc = 0
                    var i = 0
                    while (posc < ky.length) {
                        while (posc < ky.length && (ky[posc] == '<' || ky[posc] == '>' || ky[posc] == '=' || ky[posc].isDigit())) {
                            d += ky[posc]
                            posc++
                        }
                        while (posc < ky.length && !ky[posc].isDigit() && !(ky[posc] == '<' || ky[posc] == '>' || ky[posc] == '=')) {
                            posc++
                        }
                        ks.put(i, d)
                        d = ""
                        i++
                    }


                    for (i in 0..csn-1) {
                        c = ""
                        var dc = ""
                        posc = 0
                        while (posc < cs[i]!!.length) {
                            while (cs[i]!![posc] == '<' || cs[i]!![posc] == '>' || cs[i]!![posc] == '=') {
                                c += cs[i]!![posc]
                                posc++
                            }
                            while (posc < cs[i]!!.length && cs[i]!![posc].isDigit()) {
                                dc += cs[i]!![posc]
                                posc++
                            }
                            posc++
                        }
                        //println(c+dc+" "+ks[i])
                        when (c) {
                            "<" -> if (dc.toDouble() <= ks[i]!!.toDouble()) to_put=false
                            ">" -> if (dc.toDouble() >= ks[i]!!.toDouble()) to_put=false
                            "<=" -> if (dc.toDouble() < ks[i]!!.toDouble()) to_put=false
                            ">=" -> if (dc.toDouble() > ks[i]!!.toDouble()) to_put=false
                            "="  -> if (dc.toDouble() != ks[i]!!.toDouble()) to_put=false
                            else -> throw SpecialHashMapException("invalid condition")
                        }
                    }
                    if (to_put) result.put(key, value)
                }
            }
            return result
        }

        fun isNumbers(s: String, n: Int):Boolean {
            var z = 0
            for (char in s) {
                if (char == ',') {
                    z++;
                }
            }
            if (z != n - 1) return false

            val sr = s.replace(",", "").replace(" ", "").replace("(","").replace(")","")

            var numeric = true
            try {
                val num = sr.toDouble()
            } catch (e: NumberFormatException) {
                numeric = false
            }
            return numeric
        }
    }
    public var iloc = Iloc()
    public var ploc = Ploc()
}
