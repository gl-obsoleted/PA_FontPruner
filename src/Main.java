import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		int argLength = args.length;
		if(argLength<=0){
			System.out.println("输入参数有误，请输入（目标文本的文件或路径）");
		}

		StringBuffer strBuf = new StringBuffer();
		for(int i = 0;i<argLength;i++){
			File f = new File(args[i]);
			if(!f.exists()){
				System.out.println(f.toString()+"不存在");
				continue;	
			}
			if(f.isFile()){
				strBuf.append(ExtractFileContent(f));	
			}else{
				if(f.isDirectory()){
					for(File listFile :f.listFiles())
					{
						if(listFile.isFile()){
							strBuf.append(ExtractFileContent(listFile));
						}
					}
				}
			}
		}
		
		WriteStr2File(ExtractUnrepeatedWordsFromString(strBuf.toString()),new File(System.getProperty("user.dir")+"\\outPut.txt"));
//		File f= new File("C:\\Users\\zhangzhengtong\\Desktop\\fontExtractTest\\tetetr.txt");
//		String str = ExtractFileContent(f);
//		ExtractUnrepeatedWordsFromString(str);
	}
	
	public static String ExtractFileContent(File file) {
		StringBuffer strBuf =null;
		try {
			BufferedReader bufReader = 
				new BufferedReader(
					new InputStreamReader(
						new FileInputStream(
								file),"UTF-8"));

			strBuf = new StringBuffer();
			for (String tmp = null; (tmp = bufReader.readLine()) != null; tmp = null) {
				strBuf.append(tmp);
			}
			bufReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strBuf.toString();
	}
	
	public static String ExtractUnrepeatedWordsFromString(String str) {
		Map<Character, Integer> charMap = new LinkedHashMap<Character, Integer>();
		char[] charArray=str.toCharArray();
		for(char c :charArray)
		{
			if(c!= ' ' &&!charMap.containsKey(c))
			{
				charMap.put(c,1);	
			}
		}
		StringBuffer strBuf = new StringBuffer();
		for(char c :charMap.keySet()){
			strBuf.append(c);
		}
		String resultStr =strBuf.toString();
		resultStr =resultStr.replaceAll( "\\s*|\t|\r|\n","");
		resultStr = resultStr.replaceAll("[^\u4e00-\u9fa5]", "");
		System.out.println(resultStr);
		return resultStr;
	}

	
	/***
	 * 是否是汉字
	 */
	public static boolean isChineseCharacters(String str){
        boolean temp = false;
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
        Matcher m=p.matcher(str); 
        if(m.find()){ 
            temp =  true;
        }
        return temp;
	}

	public static boolean  WriteStr2File(String str,File file){
		FileWriter writer;
	    try {
	        writer = new FileWriter(file.getAbsolutePath());
	        writer.write(str);
	        writer.flush();
	        writer.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return false;
	    }
		return true;
	}
}
