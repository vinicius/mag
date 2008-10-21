--
-- Compat-5.1 release 5 (Adapted by Renato Maia from Compat-5.1 release 4)
-- Copyright Kepler Project 2004-2005 (http://www.keplerproject.org/compat)
-- According to Lua 5.1 alpha
--

_COMPAT51 = "Compat-5.1 R5 (by Renato Maia)"


local assert       = assert
local error        = error
local getfenv      = getfenv
local ipairs       = ipairs
local loadfile     = loadfile
local loadlib      = loadlib
local pairs        = pairs
local rawset       = rawset
local setfenv      = setfenv
local setmetatable = setmetatable
local type         = type


--
-- update library function changes
--

local getn = table.getn
local setn = table.setn
local oldunpack = unpack
function unpack(arg, init, term)
	local size = getn(arg)
	if
		(init and init ~= 1) or
		(term and term ~= size)
	then
		local new = {}
		if not init then init = 1 end
		size = 1 + (term or size) - init
		for i = 0, size do new[1 + i] = arg[init + i] end
		setn(new, size)
		arg = new
	end
	return oldunpack(arg)
end

function select(start, ...)
	return unpack(arg, start)
end

local find = string.find
function string.match(s, pattern, init, plain)
	return select(3, find(s, pattern, init, plain))
end
string.gmatch = string.gfind
math.fmod = math.mod


--
-- store used functions
--
local format = string.format
local match  = string.match
local gmatch = string.gfind
local gsub   = string.gsub


--
-- avoid overwriting the package table if it's already there
--
package = package or {}

package.loadlib = loadlib

local LUA_DIRSEP = '/'
local LUA_OFSEP = '_'
local LUA_POF = 'luaopen_'
local LUA_ROOT = "/usr/local/"
local LUA_LDIR = LUA_ROOT.."share/lua/5.0.2/"
local LUA_CDIR = LUA_ROOT.."lib/lua/5.0.2/"

package.path = os.getenv("LUA_PATH") or
              ("./?.lua;"..LUA_LDIR.."?.lua;"..LUA_LDIR.."?/init.lua;"..
                           LUA_CDIR.."?.lua;"..LUA_CDIR.."?/init.lua")
 
package.cpath = os.getenv("LUA_CPATH") or
              ("./?.so;"..LUA_CDIR.."?.so;"..LUA_CDIR.."loadall.so")

--
-- make sure require works with standard libraries
--
package.loaded = package.loaded or _LOADED or {}
package.loaded.package = package
package.loaded.coroutine = coroutine
package.loaded.table = table 
package.loaded.string = string
package.loaded.math = math
package.loaded.io = io
package.loaded.os = os
package.loaded.debug = debug

--
-- avoid overwriting the package.preload table if it's already there
--
package.preload = package.preload or {}


--
-- auxiliar function to read "nested globals"
--
local function getfield (t, f)
  assert (type(f)=="string", "not a valid field name ("..tostring(f)..")")
  for w in gmatch(f, "[%w_]+") do
    if not t then return nil end
    t = rawget(t, w)
  end
  return t
end


--
-- auxiliar function to write "nested globals"
--
local function setfield (t, f, v)
  for w in gmatch(f, "([%w_]+)%.") do
    t[w] = t[w] or {} -- create table if absent
    t = t[w]          -- get the table
  end
  local w = gsub(f, "[%w_]+%.", "")   -- get last field name
  t[w] = v            -- do the assignment
end


--
-- looks for a file `name' in given path
--
local function search (path, name)
  for c in gmatch(path, "[^;]+") do
    c = gsub(c, "%?", name)
    local f = io.open(c)
    if f then   -- file exist?
      f:close()
      return c
    end
  end
  return nil    -- file not found
end


--
-- check whether library is already loaded
--
local function loader_preload (name)
  assert (type(name) == "string", format (
    "bad argument #1 to `require' (string expected, got %s)", type(name)))
  if type(package.preload) ~= "table" then
    error ("`package.preload' must be a table")
  end
  return package.preload[name]
end


--
-- Lua library loader
--
local function loader_Lua (name)
  assert (type(name) == "string", format (
    "bad argument #1 to `require' (string expected, got %s)", type(name)))
  local path = LUA_PATH
  if not path then
    path = assert (package.path, "`package.path' must be a string")
  end
  local fname = gsub (name, "%.", LUA_DIRSEP)
  fname = search (path, fname)
  if not fname then
    return false
  end
  local f, err = loadfile (fname)
  if not f then
    error (format ("error loading package `%s' (%s)", name, err))
  end
  return f
end


--
-- C library loader
--
local function loader_C (name)
  assert (type(name) == "string", format (
    "bad argument #1 to `require' (string expected, got %s)", type(name)))
  local fname = gsub (name, "%.", LUA_DIRSEP)
  fname = search (package.cpath, fname)
  if not fname then
    return false
  end
  local funcname = LUA_POF .. gsub (name, "%.", LUA_OFSEP)
  local f, err = loadlib (fname, funcname)
  if not f then
    error (format ("error loading package `%s' (%s)", name, err))
  end
  return f
end


--
-- Croot library loader
--
local function loader_Croot (name)
  assert (type(name) == "string", format (
    "bad argument #1 to `require' (string expected, got %s)", type(name)))
  local rname = match(name, "(.-)%.") or name
  if rname == name then return false end
  local fname = gsub (rname, "%.", LUA_DIRSEP)
  fname = search (package.cpath, fname)
  if not fname then
    return false
  end
  local funcname = LUA_POF .. gsub (name, "%.", LUA_OFSEP)
  local f, err = loadlib (fname, funcname)
  if not f and err ~= "init" then
    error (format ("error loading package `%s' (%s)", name, err))
  end
  return f
end


-- create `loaders' table
package.loaders = package.loaders or {
	loader_preload,
	loader_Lua,
	loader_C,
	loader_Croot,
}


--
-- iterate over available loaders
--
local function load (name, loaders)
  -- iterate over available loaders
  assert (type (loaders) == "table", "`package.loaders' must be a table")
  for i, loader in ipairs (loaders) do
    local f = loader (name)
    if f then
      return f
    end
  end
  error (format ("package `%s' not found", name))
end


--
-- new require
--
function _G.require (name)
  assert (type(name) == "string", format (
    "bad argument #1 to `require' (string expected, got %s)", type(name)))
  local p = loaded[name] -- is it there?
  if p then
    return p
  end
  -- first mark it as loaded
  loaded[name] = true
  -- load and run init function
  local actual_arg = _G.arg
  _G.arg = { name }
  local res = load(name, loaders)(name)
  if res then 
    loaded[name] = res -- store result
  end
  _G.arg = actual_arg
  -- return value should be in loaded[name]
  return loaded[name]
end


--
-- new module function
--
function _G.module (name, ...)
  local _G = getfenv(0)       -- simulate C function environment
  local ns = getfield(_G, name)         -- search for namespace
  if not ns then
    ns = {}                             -- create new namespace
    setfield(_G, name, ns)
  elseif type(ns) ~= "table" then
    error("name conflict for module `"..name.."'")
  end
  if not ns._NAME then
    ns._NAME = name
    ns._M = ns
    ns._PACKAGE = gsub(name, "[^.]*$", "")
  end
  loaded[name] = ns
  setfenv(2, ns)
  for _, opt in ipairs(arg) do opt(ns) end
  return ns
end


local seeall_behavior = { __index = _G }
function package.seeall(ns)
	return setmetatable(ns, seeall_behavior)
end

--
-- define functions' environments
--
local env = {
	loaded = package.loaded,
	loaders = package.loaders,
	package = package,
	_G = _G,
}
for i, f in ipairs { _G.module, _G.require, load, loader_preload, loader_C, loader_Lua, } do
  setfenv (f, env)
end
